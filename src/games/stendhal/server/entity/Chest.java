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
package games.stendhal.server.entity;

import java.awt.*;
import java.awt.geom.*;
import marauroa.common.*;
import marauroa.common.game.*;
import marauroa.server.game.*;
import games.stendhal.server.*;
import java.util.*;

public class Chest extends Entity
  {
  private boolean open;
  
  public static void generateRPClass()
    {
    RPClass chest=new RPClass("chest");
    chest.isA("entity");
    chest.add("open",RPClass.FLAG);
    }
  
  public Chest(RPObject object) throws AttributeNotFoundException
    {
    super(object);
    put("type","chest");
    
    if(!hasSlot("content"))
      {
      addSlot(new RPSlot("content"));
      }
      
    update();
    }

  public Chest() throws AttributeNotFoundException
    {
    super();
    put("type","chest");
    open=false;
    addSlot(new RPSlot("content"));
    }

  public void getArea(Rectangle2D rect, double x, double y)
    {
    rect.setRect(x,y,1,1);
    }  

  public void update()
    {
    super.update();
    open=false;
    if(has("open")) open=true;
    }
  
  public void open()
    {
    this.open=true;
    put("open","");
    }
    
  public void close()
    {
    this.open=false;
    remove("open");
    }
  
  public boolean isOpen()
    {
    return open;
    }
  
  public void add(PassiveEntity entity)
    {
    RPSlot content=getSlot("content");
    content.assignValidID(entity);
    content.add(entity);
    }
  
  public PassiveEntity get(int i)
    {
    RPSlot content=getSlot("content");
    PassiveEntity entity=null;
    
    Iterator<RPObject> it=content.iterator();
    
    for(int j=0;j!=i && it.hasNext();j++)
      {
      entity=(PassiveEntity)it.next();     
      }    
    
    return entity;
    }
  
  public PassiveEntity remove(int i)
    {
    RPSlot content=getSlot("content");
    PassiveEntity entity=null;
    
    Iterator<RPObject> it=content.iterator();
    
    for(int j=0;j!=i && it.hasNext();j++)
      {
      entity=(PassiveEntity)it.next();     
      }    
    
    if(entity!=null)
      {
      content.remove(entity.getID());
      }
    
    return entity;
    }

  public PassiveEntity remove(PassiveEntity entity)
    {
    RPSlot content=getSlot("content");
    return (PassiveEntity)content.remove(entity.getID());
    }
  
  public int size()
    {
    return getSlot("content").size();
    }  
  
  public Iterator<RPObject> getContent()
    {
    RPSlot content=getSlot("content");
    return content.iterator();
    }
  }
