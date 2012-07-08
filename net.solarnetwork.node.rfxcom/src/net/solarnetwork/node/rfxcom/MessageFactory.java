/* ==================================================================
 * MessageFactory.java - Jul 7, 2012 2:37:01 PM
 * 
 * Copyright 2007-2012 SolarNetwork.net Dev Team
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of 
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 
 * 02111-1307 USA
 * ==================================================================
 * $Id$
 * ==================================================================
 */

package net.solarnetwork.node.rfxcom;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory for {@link Message} instances.
 * 
 * @author matt
 * @version $Revision$
 */
public class MessageFactory {

	private short sequenceNumber = 0;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public short incrementAndGetSequenceNumber() {
		return ++sequenceNumber;
	}
	
	public Message parseMessage(byte[] data, int offset) {
		if ( log.isTraceEnabled() ) {
			log.trace("Parsing RFXCOM message: " +Hex.encodeHexString(data));
		}
		if ( data == null || (data.length - offset) < 4 ) {
			if ( log.isDebugEnabled() ) {
				log.debug("Insufficient data to parse message: " +Hex.encodeHexString(data));
			}
			return null;
		}
		
		final short msgLength = data[offset];
		if ( (msgLength + offset) >= data.length ) {
			if ( log.isDebugEnabled() ) {
				log.debug("Insufficient data to parse message: " +Hex.encodeHexString(data));
			}
			return null;
		}
		
		// get offset-normalized message data, without packet header
		final byte[] msg = new byte[msgLength-4];
		System.arraycopy(data, (offset+4), msg, 0, (msgLength-4));
		
		final short sequenceNumber = data[offset+3];
		
		Message result = null;

		// packet type
		switch ( data[offset+1] ) {
			case 0x1:
				// Command response
				Command cmd = Command.valueOf(data[offset+4]);
				if ( cmd == Command.Status ) {
					result = new StatusMessage(sequenceNumber, msg);
				} else {
					result = new CommandMessage(sequenceNumber,  msg);
				}
				break;
				
			case 0x59:
				result = new CurrentMessage(data[offset+3], sequenceNumber, msg);
				break;
				
			case 0x5a:
				result = new EnergyMessage(data[offset+3], sequenceNumber, msg);
				break;
			
			default:
				if ( log.isDebugEnabled() ) {
					log.debug("Unsupported message type: " +String.format("%x", msg[1]));
				}
		}
		
		return result;
	}

	public short getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(short sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
}