package Entity.FreeMoving.AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.Floor;
import Entity.WorldObjects.Lot.Lot;
import Entity.WorldObjects.Objects.Wall;
import Utils.Vector4I;

public class PathFinding {
	public static ArrayList<Vector2f> aStar(Entity entity, Lot lot, Vector2f start, Vector2f end) {
		System.out.println("END " + end);
		
		Floor floor = lot.getFloor(entity.getPosition3D());
		NodeGrid grid = generateNodeGrid(floor);
		
		grid.checkClearence();
		
		Node startNode = grid.getNode(start);
		Node endNode = grid.getNode(end);
		
		boolean endUnWalkable = !endNode.isWalkable();
		if(endUnWalkable)
			endNode.setWalkable(true);
		
		ArrayList<Node> open = new ArrayList<>();
		ArrayList<Node> closed = new ArrayList<>();
		open.add(startNode);

		while(true) {
			open.sort(Node.COMPARATOR);

			Node currentNode;
			if(open.isEmpty())//No more tiles (path impossible)
				return null;
			else 
				currentNode = open.remove(0);
			
			closed.add(currentNode);
			if(currentNode == endNode) break;
			
			for(Node node : grid.getNeighborsNoCorners(currentNode)) {
				if(!node.isWalkable() || closed.contains(node) || node.getClearence() < entity.getBody().getSquareSize()) continue;
				
				float newCost = currentNode.getGCost() + Node.getDistance(currentNode, node);
				if(newCost < node.getGCost() || !open.contains(node)) {
					node.setGCost(newCost);
					node.setHCost(Node.getDistance(node, endNode));
					
					node.setParent(currentNode);
					if(!open.contains(node))
						open.add(node);
				}
			}
		}
		
		ArrayList<Vector2f> path = new ArrayList<>();

		Node node = closed.get(closed.size() - 1);
		path.add(new Vector2f(node.getX() + lot.getPosition().x, node.getY() + lot.getPosition().y));
		while(true) {
			Node temp = node.getParent();
			if(temp == null)
				break;
			else {
				node = temp;
				path.add(new Vector2f(node.getX() + lot.getPosition().x, node.getY() + lot.getPosition().y));
			}
		}
		
		if(endUnWalkable)
			path.remove(0);
		
		Collections.reverse(path);
		return path;
	}

	
	public static ArrayList<Vector2f> getEffectiveArea(WorldObject worldObject, Vector2f radius, boolean pointSource) {
		Lot lot = worldObject.getLot();
		Floor floor = lot.getFloor(worldObject.getPosition3D());
		
		if(pointSource) {
			return getEffectiveArea(floor, worldObject.getPosition2D(), new Vector4I(radius.x, radius.y, radius.y + worldObject.getHeight() - 1, radius.x + worldObject.getWidth() - 1));
		} else {
			Vector2f front = worldObject.getFront();
			if(front.x > 0) {//right
				return getEffectiveArea(floor, worldObject.getPosition2D(), new Vector4I(0, radius.y, radius.y + worldObject.getHeight() - 1, radius.x + worldObject.getWidth() - 1));
			} else if(front.x < 0) {//Left
				return getEffectiveArea(floor, worldObject.getPosition2D(), new Vector4I(radius.x, radius.y, radius.y + worldObject.getHeight() - 1, worldObject.getHeight() - 1));
			} else if(front.y < 0) {//Up
				return getEffectiveArea(floor, worldObject.getPosition2D(), new Vector4I(radius.x, radius.y, worldObject.getHeight() - 1, radius.x + worldObject.getWidth() - 1));
			} else {//Down
				return getEffectiveArea(floor, worldObject.getPosition2D(), new Vector4I(radius.x, 0, radius.y + worldObject.getHeight() - 1, radius.x + worldObject.getWidth() - 1));
			}
		}
	}
	
	/**
	 *      Y
	 *    X   W
	 *      Z
	 */
	public static ArrayList<Vector2f> getEffectiveArea(Floor floor, Vector2f position, Vector4I radius) {
		int px = (int) position.x;
		int py = (int) position.y;
		
		Node[][] tempNodes = new Node[radius.x + radius.w + 1][radius.y + radius.z + 1];
		
		for(int x = px - radius.x; x <= radius.w + px; x++) {
			for(int y = py - radius.y; y <= radius.z + py; y++) {
				if(floor.getTiles()[x][y].getObject() instanceof Wall) 
					tempNodes[x - px + radius.x][y - py + radius.y] = new Node(x - px + radius.x, y - py + radius.y, false);
				else 
					tempNodes[x - px + radius.x][y - py + radius.y] = new Node(x - px + radius.x, y - py + radius.y, true);
			}
		}
		
		NodeGrid grid = new NodeGrid(tempNodes);
		ArrayList<Node> nodes = new ArrayList<>();
		addNeighbores(grid, grid.getNode(new Vector2f(radius.x, radius.y)), nodes);

		return toVector(nodes, new Vector2f(px - radius.x, py - radius.y));
 	}
	
	private static ArrayList<Node> addNeighbores(NodeGrid grid, Node node, ArrayList<Node> toAdd) {
		ArrayList<Node> temp = grid.checkNeighbores(node);
		toAdd.addAll(temp);
		
		for(Node tempNode : temp) 
			addNeighbores(grid, tempNode, toAdd);
		
		return temp;
	}
	
	private static ArrayList<Vector2f> toVector(ArrayList<Node> nodes, Vector2f translate) {
		ArrayList<Vector2f> temp = new ArrayList<>();
		for(Node n : nodes) {
			if(translate != null)
				temp.add(n.getPosition().add(translate));
			else
				temp.add(n.getPosition());
		}
		return temp;
	}
	
	private static NodeGrid generateNodeGrid(Floor floor) {
		Node[][] field = new Node[floor.getTiles().length][floor.getTiles()[0].length];
		
		for(int x = 0; x < floor.getTiles().length; x++) 
		for(int y = 0; y < floor.getTiles()[x].length; y++) 
			field[x][y] = new Node(floor.getTiles()[x][y].getX(), floor.getTiles()[x][y].getZ(), !floor.getTiles()[x][y].containsAnything());
		
		return new NodeGrid(field);
	}
	
	/*
	 * Simplifies the path down to the important vertices
	 * Gets rid of the origin (No need to go to where we already are)
	 */
	public static ArrayList<Vector2f> simplifyPath(ArrayList<Vector2f> pathPoints) {
		if(pathPoints == null)
			return null;
		
		ArrayList<Vector2f> path = new ArrayList<>(pathPoints);
		
		Vector2f lastDistance = null;
		for(int i = 0; i < path.size() - 1; i++) {
			Vector2f point = path.get(i);
			Vector2f nextPoint = path.get(i + 1);
			
			if(lastDistance == null) { 
				lastDistance = nextPoint.subtract(point);
			} else if(nextPoint.subtract(point).equals(lastDistance)) {
				path.remove(point);
				i--;
			} else 
				lastDistance = nextPoint.subtract(point);
		}
		
		path.remove(0);
		return path;
	}
}

class NodeGrid {
	private Node[][] nodes;
	
	public NodeGrid(Node[][] nodes) {
		this.nodes = nodes;
	}
	
	public void checkClearence() {
		for(int x = 0; x < nodes.length; x++) {
			for(int z = 0; z < nodes[x].length; z++) {
				Node node = getNode(x / 2f, z / 2f);
				
				float radius = .5f;
				while(isClear(node, radius)) 
					radius += .5f;
				node.setClearence(radius -.5f);
				
				if(node.isWalkable())
					System.out.print(node.getClearence() + " ");
				else 
					System.out.print(0.0 + " ");
			}
			System.out.println();
		}
	}
	
	private boolean isClear(Node node, float radius) {
		for(float x = node.getX() + .5f; x < node.getX() + radius; x += .5) {
			for(float z = node.getY(); z < node.getY() + radius; z += .5) {
				Node tempNode = getNode(x, z); 
				if(tempNode == null || !tempNode.isWalkable())
					return false;
			}
		}
		return true;
	}
	
	public ArrayList<Vector2f> asVectors(ArrayList<Node> nodes) {
		ArrayList<Vector2f> temp = new ArrayList<>();
		
		for(Node node : nodes) 
			temp.add(node.getPosition());
		return temp;
	}
	
	public ArrayList<Node> getNeighbors(Node node) {
		ArrayList<Node> neighbores = new ArrayList<>();
		
		for(float x = node.getX() - .5f; x <= node.getX() + .5; x += .5f) {
			for(float y = node.getY() - .5f; y <= node.getY() + .5; y += .5f) {
				Node temp = getNode(x,y);
				
				if(temp != null && temp != node)
					neighbores.add(temp);
			}
		}
		
		return neighbores;
	}
	
	public ArrayList<Node> checkNeighbores(Node node) {
		ArrayList<Node> temp = getAdjacentNeighbores(node);
		for(int i = temp.size() - 1; i >= 0; i--) {
			if(!temp.get(i).isWalkable() || temp.get(i).isChecked()) 
				temp.remove(i);
			else 
				temp.get(i).setChecked(true);
		}
		return temp;
	}
	
	/**
	 * Gets all the neighborers, but not the ones that are "technically possible" (can't cut through corners in between objects)
	 */
	public ArrayList<Node> getNeighborsNoCorners(Node node) {
		ArrayList<Node> neighbores = new ArrayList<>();
		
		for(float x = node.getX() - .5f; x <= node.getX() + .5; x += .5) {
			for(float y = node.getY() - .5f; y <= node.getY() + .5; y += .5) {
				Node temp = getNode(x,y);
				
				if(x != node.getX() && y != node.getY()) {
					if(x > node.getX()) {
						if(y > node.getY()) { 
							if(!isWalkable(x - .5f, y) || !isWalkable(x, y - .5f))
								continue;
						} else if(!isWalkable(x - .5f, y) || !isWalkable(x, y + .5f))
							continue;
					} else if(x < node.getX()) {
						if(y > node.getY()) {
							if(!isWalkable(x + .5f, y) || !isWalkable(x, y - .5f))
								continue;
						} else if(y < node.getY()) {
							if(!isWalkable(x + .5f, y) || !isWalkable(x, y + .5f))
								continue;
						}
					}
				}
				
				if(temp != null && temp != node)
					neighbores.add(temp);
			}
		}
		
		return neighbores;
	}
	
	/**
	 * Get only the 4 directly adjacent nodes
	 */
	public ArrayList<Node> getAdjacentNeighbores(Node node) {
		ArrayList<Node> temp = new ArrayList<>();
		
		temp.add(getNode(node.getPosition().add(.5, 0)));
		temp.add(getNode(node.getPosition().add(-.5, 0)));
		temp.add(getNode(node.getPosition().add(0, .5)));
		temp.add(getNode(node.getPosition().add(0, -.5)));
		
		for(int i = temp.size() - 1 ; i >= 0; i--)
			if(temp.get(i) == null) temp.remove(i);
		return temp;
	}
	
	public Node getNode(float fx, float fy) {
		int x = (int) (fx * 2);
		int y = (int) (fy * 2);
		
		if(x >= 0 && x < nodes.length && y >= 0 && y < nodes[0].length)
			return nodes[x][y];
		return null;
	}
	
	public boolean isWalkable(float x, float y) {
		Node node = getNode(x, y);
		if(node == null) return false;
		return node.isWalkable();
	}
	
	public Node getNode(Vector2f pos) {
		return getNode(pos.x, pos.y);
	}
	
	public Node[][] getNodes() { return nodes; }
}

class Node {
	public static final Comparator<Node> COMPARATOR = (o1, o2) -> {
		if(o1 == o2)
			return 0;
		if(o1.getFValue() == o2.getFValue()) { 
			if(o1.getHCost() < o2.getHCost())
				return -1;
			else
				return 1;
		} else if(o1.getFValue() < o2.getFValue())
			return -1;
		else 
			return 1;
	};
	
	private Node parent;

	private float x, y;
	private float gCost, hCost;
	private boolean walkable;
	private boolean checked;
	
	private float clearence;
	
	public Node(float x, float y, boolean walkable) {
		this.x = x; 
		this.y = y;
		this.walkable = walkable;
	}
	
	public static float getDistance(Node node1, Node node2) {
		float dX = Math.abs(node1.getX() - node2.getX());
		float dY = Math.abs(node1.getY() - node2.getY());
		
		if(dX > dY) 
			return 14 * dY + 10 * (dX - dY);
		return 14 * dX + 10 * (dY - dX);
	}
	
	public Node getParent() { return parent; }
	public void setParent(Node parent) { this.parent = parent; } 
	
	public float getFValue() { return gCost + hCost; }
	public float getGCost() { return gCost; }
	public void setGCost(float gCost) { this.gCost = gCost; }
	public float getHCost() { return hCost; }
	public void setHCost(float hCost) { this.hCost = hCost; }
	
	public void setWalkable(boolean walkable) { this.walkable = walkable; }
	public boolean isWalkable() { return walkable; }
	
	public float getClearence() { return clearence; }
	public void setClearence(float clearence) { this.clearence = clearence; }
	
	public Vector2f getPosition() { return new Vector2f(x, y); }
	public float getX() { return x; }
	public float getY() { return y; }
	
	public boolean isChecked() { return checked; }
	public void setChecked(boolean checked) { this.checked = checked; }
	
	@Override
	public boolean equals(Object other) { return getPosition().equals(((Node) other).getPosition()); }
}