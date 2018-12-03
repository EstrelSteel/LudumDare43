package com.estrelsteel.ld43.world;

import java.util.ArrayList;
import java.util.HashMap;

import com.estrelsteel.engine2.chunk.ImageChunk;
import com.estrelsteel.engine2.file.GameFile;
import com.estrelsteel.engine2.file.Saveable;
import com.estrelsteel.engine2.image.Image;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.tile.Tile;
import com.estrelsteel.engine2.tile.TileType;

public class LevelBuilder implements Saveable {

	private HashMap<Integer, Integer> colourMap;
	private int tileWidth;
	private int tileHeight;
	private static final int CHUNK_WIDTH = 10;
	private static final int CHUNK_HEIGHT = 10;
	
	public LevelBuilder(int tileWidth, int tileHeight) {
		colourMap = new HashMap<Integer, Integer>();
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}
	
	public HashMap<Integer, Integer> getColourMap() {
		return colourMap;
	}
	
	public ArrayList<ImageChunk> generateChunkLevel(Image img) {
		ArrayList<ImageChunk> c = new ArrayList<ImageChunk>();
		ArrayList<Tile> tiles = generateLevel(img);
		for(int x = 0; x < img.getImage().getWidth(); x = x + CHUNK_WIDTH) {
			for(int y = 0; y < img.getImage().getHeight(); y = y + CHUNK_HEIGHT) {
				c.add(0, new ImageChunk(QuickRectangle.location(x * tileWidth, y * tileHeight, CHUNK_WIDTH * tileWidth, CHUNK_HEIGHT * tileHeight)));
				c.get(0).getObjects().addAll(tiles);
			}
		}
		return c;
	}
	
	public ArrayList<Tile> generateLevel(Image img) {
		if(!img.isImageLoaded()) img.loadImage();
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		Integer tt;
		for(int x = 0; x < img.getImage().getWidth(); x++) {
			for(int y = 0; y < img.getImage().getHeight(); y++) {
				tt = colourMap.get(img.getImage().getRGB(x, y));
//				if(img.getImage().getRGB(x, y) != 0) System.out.println(img.getImage().getRGB(x, y));
				if(tt != null) {
					tiles.add(new Tile(TileType.types.get(tt), QuickRectangle.location(x * tileWidth, y * tileHeight, tileWidth, tileHeight)));
				}
			}
		}
		return tiles;
	}
	
	@Override
	public String getIdentifier() {
		return "BUI";
	}

	@Override
	public LevelBuilder load(GameFile file, int line) {
		if(file.getLines().get(line).startsWith(getIdentifier())) {
			String[] args = file.getLines().get(line).split(" ");
			tileWidth = Integer.parseInt(args[1]);
			tileHeight = Integer.parseInt(args[2]);
			for(line++; line < file.getLines().size(); line++) {
				args = file.getLines().get(line).split(" ");
				colourMap.put(255 << 24 | Integer.parseInt(args[1]) << 16 | Integer.parseInt(args[2]) << 8 | Integer.parseInt(args[3]), Integer.parseInt(args[0]));
			}
		}
		return this;
	}

	@Override
	public GameFile save(GameFile file) {
		return file;
	}

}
