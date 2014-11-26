import java.util.ArrayList;
import java.util.Random;

/**
 * A SkipList is a randomized multi-level linked list
 * 
 * @param <T>
 *            The generic type of the list.
 */
public class SkipList<T extends Comparable<? super T>>
{

	private Node root; // dummy header node. You may want a dummy tail node as
						// well.
	private int numNodesVisited;
	private Random randomizer;
	private Node tail; // dummy tail

	private static int MAX_LINKS = 10;

	/**
	 * Creates a skip list with truly random numbers. We won't use this
	 * constructor for our tests.
	 */
	public SkipList()
	{
		this.randomizer = new Random();
		reset();
	}

	/**
	 * Creates a SkipList with a given fixed seed. This is better for testing.
	 * 
	 * @param seed
	 *            A random seed.
	 */
	public SkipList(int seed)
	{
		this.randomizer = new Random(seed);
		reset();
	}

	// Creates the skip list's root and end nodes and does any other
	// initialization needed.
	private void reset()
	{
		// DONE: implement
		this.root = new Node();
		for(int i = 0 ;i < MAX_LINKS; i++)
		{
			this.root.links.add(this.tail);
		}
	}

	/**
	 * Grabs the next random integer from the array
	 * 
	 * @return 0 or 1.
	 */
	public int getRand()
	{
		int temp = this.randomizer.nextInt(2);
		// Uncomment to see random values generated.
		// System.out.println("Random = " + temp);
		return temp;
	}

	/**
	 * @return the root node
	 */
	public Node getRoot()
	{
		return this.root;
	}

	/**
	 * Inserts the elements in the array in the SkipList in order, then iterates
	 * through the list, copying them back into the array, thus sorting the
	 * array.
	 * 
	 * @param array
	 */
	public void sort(T[] array)
	{
		// (For the next assignment, not this one.)

		// TODO: Increase MAX_LINKS, since 10 will be too small when sorting
		// large arrays.

		this.reset();
		// TODO: finish implementing sort.

	}

	/**
	 * Inserts the given element in the list
	 * 
	 * @param e
	 * @return true if successful (always successful since you can add doubles
	 */
	public boolean insert(T e)
	{
		//DONE: implement
		Node newNode = new Node(e);
		int height = this.height();
		int level = this.root.getLinks().size() - 2;

		Node current = this.root;
		while (true)
		{
			// make a next node so you can keep track of the current node you're
			// at and the node to its right
			Node next = current.getLinks().get(level);

			if (next == tail)
			{
				// where tail is a dummy node
				if (level <= height)
				{
					this.setLinks(current, next, newNode, level);
				}
				if (level == 0)
				{
					return true;
				}
				level--;
				// skip everything else in the while loop
				continue;
			}

			if (e.compareTo(next.getElement()) <= 0)
			{
				if (level <= height)
				{
					this.setLinks(current, next, newNode, level);
				}

				if (level == 0)
				{
					// at lowest level
					return true;
				}
				level--;
			}
			else
			{
				this.numNodesVisited++;
				current = next;

			}
		}
	}

	/**
	 * Removes the given element from the list
	 * 
	 * @param e
	 * @return true if successful, false otherwise
	 */
	public boolean remove(T e)
	{
		// DONE: implement.
		Node current = this.root;
		int level = SkipList.MAX_LINKS - 2;
		while (level >= 0)
		{
			Node next = current.getLinks().get(level);
			if (next == null)
			{
				level--;
				// skip the rest of the while loop
				continue;
			}

			if (next.getElement().compareTo(e) < 0)
			{
				current = next;
				this.numNodesVisited++;

			}
			else if (next.getElement().compareTo(e) > 0)
			{
				level--;
			}
			else
			{
				// remove
				this.removeNode(current, next, level);
				if (level == 0)
				{
					return true; // return true if bottom level;
				}
			}
		}
		return false;
	}

	/**
	 * @return the number of nodes visited during insertion and removal
	 */
	public int getNodesVisited()
	{
		return this.numNodesVisited;
	}

	public int height()
	{
		int heightCount = 0;
		while (this.getRand() == 1)
		{
			heightCount++;
		}
		if (heightCount >= MAX_LINKS - 1)
		{
			heightCount = MAX_LINKS - 2;
		}
		return heightCount;
	}

	public void setLinks(Node cur, Node next, Node newNode, int level)
	{
		// set links from previous to the new added
		cur.getLinks().set(level, newNode); // cur--> newNode--> next;
		newNode.getLinks().add(0, next);
	}

	public void removeNode(Node prev, Node target, int level)
	{
		prev.getLinks().set(level, target.getLinks().get(level));
	}

	@Override
	public String toString()
	{
		// DONE: implement.
		StringBuilder sb = new StringBuilder();
		Node current = this.root.getLinks().get(0);
		sb.append("[");
		while (current != this.tail)
		{
			sb.append(current.getElement());
			sb.append(", ");
			current = current.getLinks().get(0);
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * A Node holds data and links to the next node on its levels.
	 * 
	 */
	public class Node
	{
		private T element;
		private ArrayList<Node> links;

		/**
		 * Null node constructor
		 * 
		 */
		public Node()
		{
			this.links = new ArrayList<Node>();
		}

		// DONE: implement whatever else is needed for this class.
		// You should not add any more fields.
		/**
		 * Instantiates this node with the given element and links
		 * 
		 * @param e
		 * @param size
		 */
		public Node(T e)
		{
			this.element = e;
			this.links = new ArrayList<Node>();
		}

		/**
		 * Returns the list of links this node is holding
		 * 
		 * @return a list of links
		 */
		public ArrayList<Node> getLinks()
		{
			return this.links;
		}

		/**
		 * @return this node's element
		 */
		public T getElement()
		{
			return this.element;
		}
	}
}