package com.mystudio.themaze;

import java.util.ArrayList;

import org.mini2Dx.core.game.BasicGame;
import org.mini2Dx.core.graphics.Graphics;

public class Game extends BasicGame {
	public static final String GAME_IDENTIFIER = "com.mystudio.themaze";
	private Maze maze;
	private Player player;
	test test;
	private Collision collision;
	private ArrayList<Enemy> enemies;
	private Song songStartGame;
	
	@Override
    public void initialise() 
	{	
		mapTranslator map = new mapTranslator("map/m1SK.png");
		map.translate();
		
    	maze = new Maze(map);
    	test = new test();
    	test.createDictionnaire(maze);
    	
    	player = new Player(maze.getPlayerSpawnX(), maze.getPlayerSpawnY(), maze.getMapScale(), maze);
    	enemies = new ArrayList<Enemy>();
    	enemies.add(new Enemy(maze.getEnemySpawnX(0), maze.getEnemySpawnY(0), maze.getMapScale(), maze));
    	enemies.add(new Enemy(maze.getEnemySpawnX(1), maze.getEnemySpawnY(1), maze.getMapScale(), maze));
    	
    	maze.addItem(new Item(23, 27, maze.getMapScale(), "item/key.png"));
    	maze.addItem(new Item(23, 28, maze.getMapScale(), "item/sword.png"));
    	maze.addItem(new Item(23, 29, maze.getMapScale(), "item/potion.png"));
    	
    	collision = new Collision(maze.getItems(), player, enemies, maze.getMapScale());
    	
    	songStartGame = new Song("pacman.wav");
    	songStartGame.startLoop();
    	System.out.println(test.getNextPostions(1, 2));
    	
    }
    
    @Override
    public void update(float delta) 
    {
    	if(player.getAlive() && !player.getEscape())
    	{
	    	player.update();
	    	for(int i=0; i<enemies.size(); i++)
	    		enemies.get(i).update();
	    	collision.verify();    
	    	collision.verifyEnemy();
    	}
    }
    
    @Override
    public void interpolate(float alpha) 
    {
    	
    }
    
    @Override
    public void render(Graphics g) 
    {
		//maze.PaintMaze(g);
  
		maze.displayUserMap(g, player);
    	
		player.render(g);
		maze.displayItems(g);
		for(int i=0; i<enemies.size(); i++)
			if(enemies.get(i).getAlive())
				enemies.get(i).render(g);
		
		maze.displayUserMapSecondLayer(g, player);			
    }	
}
