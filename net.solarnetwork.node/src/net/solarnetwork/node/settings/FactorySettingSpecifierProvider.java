/* ==================================================================
 * FactorySettingSpecifierProvider.java - Mar 23, 2012 3:06:27 PM
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

package net.solarnetwork.node.settings;

/**
 * Extension of {@link SettingSpecifierProvider} that adds info about the
 * factory instance a provider is being managed under.
 * 
 * @author matt
 * @version $Revision$
 */
public interface FactorySettingSpecifierProvider extends SettingSpecifierProvider {

	/**
	 * Get the factory-wide unique ID for this provider.
	 * 
	 * @return the factory-wide unique ID
	 */
	String getFactoryInstanceUID();

}
