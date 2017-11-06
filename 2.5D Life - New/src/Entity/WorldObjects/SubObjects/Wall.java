package Entity.WorldObjects.SubObjects;

import com.Engine.RenderEngine.Shaders.Shader;
import com.Engine.Util.Vectors.Vector2f;

import Entity.WorldObjects.SubTileObject;
import World.Tiles.Tile;

public class Wall extends SubTileObject {

	public Wall(int subX, int subY, String name, Shader modelShader) {
		super(subX, subY, new Vector2f(Tile.TILE_RESOLUTION, Tile.TILE_RESOLUTION / 2).divide(Tile.TILE_RESOLUTION), name, modelShader);
		
	}
}
