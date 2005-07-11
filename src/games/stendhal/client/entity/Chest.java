/* $Id$ */
/***************************************************************************
 *                      (C) Copyright 2003 - Marauroa                      *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.client.entity;

import marauroa.common.game.*;
import games.stendhal.client.*;
import java.awt.*;
import java.awt.geom.*;

public class Chest extends AnimatedEntity 
  {
  private boolean open;
  private RPSlot content;
  
  public Chest(GameObjects gameObjects, RPObject object) throws AttributeNotFoundException
    {
    super(gameObjects, object);
    }
  
  protected void buildAnimations(RPObject object)
    {
    SpriteStore store=SpriteStore.get();  

    sprites.put("close", store.getAnimatedSprite(translate(object.get("type")),0,1,32,32));      
    sprites.put("open", store.getAnimatedSprite(translate(object.get("type")),1,1,32,32));      
    }
  
  protected Sprite defaultAnimation()
    {
    animation="close";
    return sprites.get("close")[0];
    }

  public void modifyAdded(RPObject object, RPObject changes) throws AttributeNotFoundException
    {
    super.modifyAdded(object,changes);
    
    if(changes.has("open"))
      {
      open=true;
      animation="open";
      }
    
    if(changes.hasSlot("content"))
      {
      content=changes.getSlot("content");
      }
    }

  public void modifyRemoved(RPObject object, RPObject changes) throws AttributeNotFoundException
    {
    super.modifyAdded(object,changes);
    
    if(changes.has("open"))
      {
      open=false;
      animation="close";
      }
    }

  public Rectangle2D getArea()
    {
    return new Rectangle.Double(x,y,1,1);
    }
    
  public Rectangle2D getDrawedArea()
    {
    return new Rectangle.Double(x,y,1,1);
    }  

  public String defaultAction()
    {
    return "Look";
    }

  public String[] offeredActions()
    {    
    String[] list=null;
    if(open)
      {
      list=new String[]{"Look","Close"};
      }
    else
      {
      list=new String[]{"Look","Open"};
      }
      
    return list;
    }

  public void onAction(StendhalClient client, String action, String... params)
    {
    if(action.equals("Look"))
      {
      String text="You see a chest that is "+(open?"open.":"closed.");
      StendhalClient.get().addEventLine(text,Color.green);
      gameObjects.addText(this, text, Color.green);
      }
    else if(action.equals("Open") || action.equals("Close"))
      {
      if(!open)
        {
        client.getGameGUI().inspect(this,content);
        }
      else
        {
        client.getGameGUI().inspect(null,null);
        }              
        
      RPAction rpaction=new RPAction();
      rpaction.put("type","use");
      int id=getID().getObjectID();
      rpaction.put("object",id);      
      client.send(rpaction);
      }
    }

  public int compare(Entity entity)
    {
    if(entity instanceof RPEntity)
      {
      return -1;
      }
      
    return 1;
    }
  }
