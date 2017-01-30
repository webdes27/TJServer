/*
 * This file is part of TJServer.
 * 
 * TJServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * TJServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package tera.gameserver.network.clientpackets;

import tera.gameserver.model.actions.Action;
import tera.gameserver.model.playable.Player;

/**
 * @author Ronn
 */
public class RequestActionAgree extends ClientPacket
{
	private Player player;
	private Action action;
	private int answer;
	private int objectId;
	
	/**
	 * Method finalyze.
	 * @see rlib.util.pools.Foldable#finalyze()
	 */
	@Override
	public void finalyze()
	{
		player = null;
		action = null;
	}
	
	@Override
	protected void readImpl()
	{
		player = owner.getOwner();
		readShort();
		readByte();
		readShort();
		readByte();
		objectId = readInt();
		readInt();
		answer = readByte();
	}
	
	@Override
	protected void runImpl()
	{
		if (player == null)
		{
			return;
		}
		
		action = player.getLastAction();
		
		if ((action == null) || (action.getObjectId() != objectId))
		{
			return;
		}
		
		if (answer == 1)
		{
			action.assent(player);
		}
		else if (answer == 2)
		{
			action.cancel(player);
		}
	}
}