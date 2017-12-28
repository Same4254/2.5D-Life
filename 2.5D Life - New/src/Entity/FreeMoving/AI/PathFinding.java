package Entity.FreeMoving.AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.Engine.Util.Vectors.Vector2f;

import Entity.FreeMoving.Entity;
import Entity.WorldObjects.WorldObject;
import Entity.WorldObjects.Lot.Lot;
import Entity.WorldObjects.Objects.Wall;
import Utils.Vector4I;

public class PathFinding {
	public static ArrayList<Vector2f> aStar(Entity e, Lot lot, Vector2f start, Vector2f end) {
		NodeGrid grid = generateNodeGrid(lot);
		
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
				if(!node.isWalkable() || closed.contains(node)) continue;
				
				int newCost = currentNode.getGCost() + Node.getDistance(currentNode, node);
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

	
	public static ArrayList<Vector2f> getEffectiveArea(Lot lot, WorldObject worldObject, Vector2f radius, boolean pointSource) {
		if(pointSource) {
			return getEffectiveArea(lot, worldObject.getPosition2D(), new Vector4I(radius.x, radius.y, radius.y + worldObject.getHeight() - 1, radius.x + worldObject.getWidth() - 1));
		} else {
			Vector2f front = worldObject.getFront();
			if(front.x > 0) {//right
				return getEffectiveArea(lot, worldObject.getPosition2D(), new Vector4I(0, radius.y, radius.y + worldObject.getHeight() - 1, radius.x + worldObject.getWidth() - 1));
			} else if(front.x < 0) {//Left
				return getEffectiveArea(lot, worldObject.getPosition2D(), new Vector4I(radius.x, radius.y, radius.y + worldObject.getHeight() - 1, worldObject.getHeight() - 1));
			} else if(front.y < 0) {//Up
				return getEffectiveArea(lot, worldObject.getPosition2D(), new Vector4I(radius.x, radius.y, worldObject.getHeight() - 1, radius.x + worldObject.getWidth() - 1));
			} else {//Down
				return getEffectiveArea(lot, worldObject.getPosition2D(), new Vector4I(radius.x, 0, radius.y + worldObject.getHeight() - 1, radius.x + worldObject.getWidth() - 1));
			}
		}
	}
	
	/**
	 *      Y
	 *    X   W
	 *      Z
	 */
	public static ArrayList<Vector2f> getEffectiveArea(Lot lot, Vector2f position, Vector4I radius) {
		int px = (int) position.x;
		int py = (int) position.y;
		
		Node[][] tempNodes = new Node[radius.x + radius.w + 1][radius.y + radius.z + 1];
		
		for(int x = px - radius.x; x <= radius.w + px; x++) {
			for(int y = py - radius.y; y <= radius.z + py; y++) {
				if(lot.getTiles()[x][y].getObject() instanceof Wall) 
					tempNodes[x - px + radius.x][y - py + radius.y] = new Node(x - px + radius.x, y - py + radius.y, false);
				else 
					tempNodes[x - px + radius.x][y - py + radius.y] = new Node(x - px + radius.x, y - py + radius.y, true);
			}
		}
		
		NodeGrid grid = new NodeGrid(tempNodes);
		ArrayList<Node> nodes = new ArrayList<>();
		addNeighbores(lot, grid, grid.getNode(new Vector2f(radius.x, radius.y)), nodes);
		
		//**********************
		//		  TEST
		System.out.println("Nodes: " + grid.getNodes());
		for(Node[] nodeArray : grid.getNodes()) {
			for(Node n : nodeArray) {
				if(n.isWalkable())
					System.out.print("0 ");
				else
					System.out.print("1 ");
			}
			System.out.println();
		}
		
		System.out.println("------");
		
		for(Node[] nodeArray : grid.getNodes()) {
			for(Node n : nodeArray) {
				boolean contains = false;
				for(Node node : nodes) {
					if(node.getPosition().x == n.getPosition().x && node.getPosition().y == n.getPosition().y) {
						contains = true;
						break;
					}
				}
				
				if(contains) {
					System.out.print("2 ");
				} else {
					System.out.print("0 ");
				}
			}
			System.out.println();
		}
		
		//**********************
		
		return toVector(nodes, new Vector2f(px - radius.x, py - radius.y));
 	}
	
	private static ArrayList<Node> addNeighbores(Lot lot, NodeGrid grid, Node node, ArrayList<Node> toAdd) {
		ArrayList<Node> temp = grid.checkNeighbores(node);
		toAdd.addAll(temp);
		
		for(Node tempNode : temp) 
			addNeighbores(lot, grid, tempNode, toAdd);
		
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
	
	private static NodeGrid generateNodeGrid(Lot lot) {
		Node[][] field = new Node[lot.getTiles().length][lot.getTiles()[0].length];
		
		for(int x = 0; x < lot.getTiles().length; x++) 
		for(int y = 0; y < lot.getTiles()[x].length; y++) 
			field[x][y] = new Node(x, y, !lot.getTiles()[x][y].containsAnything());
		
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
	
	public ArrayList<Vector2f> asVectors(ArrayList<Node> nodes) {
		ArrayList<Vector2f> temp = new ArrayList<>();
		
		for(Node node : nodes) 
			temp.add(node.getPosition());
		return temp;
	}
	
	public ArrayList<Node> getNeighbors(Node node) {
		ArrayList<Node> neighbores = new ArrayList<>();
		
		for(int x = node.getX() - 1; x <= node.getX() + 1; x++) {
			for(int y = node.getY() - 1; y <= node.getY() + 1; y++) {
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
		
		for(int x = node.getX() - 1; x <= node.getX() + 1; x++) {
			for(int y = node.getY() - 1; y <= node.getY() + 1; y++) {
				Node temp = getNode(x,y);
				
				if(x != node.getX() && y != node.getY()) {
					if(x > node.getX()) {
						if(y > node.getY()) { 
							if(!isWalkable(x - 1, y) || !isWalkable(x, y - 1))
								continue;
						} else if(!isWalkable(x - 1, y) || !isWalkable(x, y + 1))
							continue;
					} else if(x < node.getX()) {
						if(y > node.getY()) {
							if(!isWalkable(x + 1, y) || !isWalkable(x, y - 1))
								continue;
						} else if(y < node.getY()) {
							if(!isWalkable(x + 1, y) || !isWalkable(x, y + 1))
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
		
		temp.add(getNode(node.getPosition().add(1, 0)));
		temp.add(getNode(node.getPosition().add(-1, 0)));
		temp.add(getNode(node.getPosition().add(0, 1)));
		temp.add(getNode(node.getPosition().add(0, -1)));
		
		for(int i = temp.size() - 1 ; i >= 0; i--)
			if(temp.get(i) == null) temp.remove(i);
		return temp;
	}
	
	public Node getNode(int x, int y) {
		if(x >= 0 && x < nodes.length && y >= 0 && y < nodes[0].length)
			return nodes[x][y];
		return null;
	}
	
	public boolean isWalkable(int x, int y) {
		Node node = getNode(x, y);
		if(node == null) return false;
		return node.isWalkable();
	}
	
	public Node getNode(Vector2f pos) {
		return getNode((int) pos.x, (int) pos.y);
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

	private int x, y;
	private int gCost, hCost;
	private boolean walkable;
	private boolean checked;
	
	public Node(int x, int y, boolean walkable) {
		this.x = x; 
		this.y = y;
		this.walkable = walkable;
	}
	
	public static int getDistance(Node node1, Node node2) {
		int dX = Math.abs(node1.getX() - node2.getX());
		int dY = Math.abs(node1.getY() - node2.getY());
		
		if(dX > dY) 
			return 14 * dY + 10 * (dX - dY);
		return 14 * dX + 10 * (dY - dX);
	}
	
	public Node getParent() { return parent; }
	public void setParent(Node parent) { this.parent = parent; } 
	
	public int getFValue() { return gCost + hCost; }
	public int getGCost() { return gCost; }
	public void setGCost(int gCost) { this.gCost = gCost; }
	public int getHCost() { return hCost; }
	public void setHCost(int hCost) { this.hCost = hCost; }
	
	public void setWalkable(boolean walkable) { this.walkable = walkable; }
	public boolean isWalkable() { return walkable; }
	
	public Vector2f getPosition() { return new Vector2f(x, y); }
	public int getX() { return x; }
	public int getY() { return y; }
	
	public boolean isChecked() { return checked; }
	public void setChecked(boolean checked) { this.checked = checked; }
	
	@Override
	public boolean equals(Object other) { return getPosition().equals(((Node) other).getPosition()); }
}