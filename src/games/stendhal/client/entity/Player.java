/* $Id$ */
/***************************************************************************
 *		      (C) Copyright 2003 - Marauroa		      *
 ***************************************************************************
 ***************************************************************************
 *									 *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.				   *
 *									 *
 ***************************************************************************/
package games.stendhal.client.entity;

import games.stendhal.client.StendhalUI;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.List;

import marauroa.common.game.AttributeNotFoundException;
import marauroa.common.game.RPAction;
import marauroa.common.game.RPObject;

/** A Player entity */
public class Player extends RPEntity {
	/**
	 * An away message was set/cleared.
	 *
	 * @param	message		The away message, or <code>null</code>
	 *				if no-longer away.
	 */
	protected void onAway(final String message) {
		addFloater(((message != null) ? "Away" : "Back"), Color.blue);
	}


	@Override
	public void onChangedAdded(final RPObject base, final RPObject diff) throws AttributeNotFoundException {
		super.onChangedAdded(base, diff);

		if (diff.has("outfit")) {
			changed();
		}

		// We redo here to mantain player whose name has an underscore
		if (diff.has("name")) {
			setName(diff.get("name"));
		}

		if (diff.has("away")) {
			/*
			 * Filter out a player "changing" to the same message
			 */
			if (!base.has("away") || !base.get("away").equals(diff.get("away"))) {
				onAway(diff.get("away"));
			}
		}

		// The first time we ignore it.
		if (base != null) {
			if (diff.has("online")) {
				String[] players = diff.get("online").split(",");
				for (String playerName : players) {
					StendhalUI.get().addEventLine(playerName + " has joined Stendhal.", Color.orange);
				}
			}

			if (diff.has("offline")) {
				String[] players = diff.get("offline").split(",");
				for (String playername : players) {
					StendhalUI.get().addEventLine(playername + " has left Stendhal.", Color.orange);
				}
			}
		}
	}

	public void onChangedRemoved(final RPObject base, final RPObject diff) {
		super.onChangedRemoved(base, diff);

		if (diff.has("away")) {
			onAway(null);
		}
	}

	@Override
	public Rectangle2D getArea() {
		return new Rectangle.Double(x, y + 1, 1, 1);
	}

	@Override
	public void onAction(final ActionType at, final String... params) {

		// ActionType at =handleAction(action);
		RPAction rpaction;
		switch (at) {
			case ADD_BUDDY:
				rpaction = new RPAction();
				rpaction.put("type", at.toString());
				rpaction.put("target", getName());
				at.send(rpaction);
				break;

			default:
				super.onAction(at, params);
				break;
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see games.stendhal.client.entity.RPEntity#buildOfferedActions(java.util.List)
	 */
	@Override
	protected void buildOfferedActions(List<String> list) {
		super.buildOfferedActions(list);

		list.add(ActionType.ADD_BUDDY.getRepresentation());
		list.add(ActionType.JOIN_GUILD.getRepresentation());
	}


	//
	// Entity
	//

	/**
	 * Transition method. Create the screen view for this entity.
	 *
	 * @return	The on-screen view of this entity.
	 */
	protected Entity2DView createView() {
		return new Player2DView(this);
	}
}
