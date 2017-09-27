import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private final int n;
	private final int gridLength;
	private boolean isPercolated;
	private boolean[] gridOpen;
	private final WeightedQuickUnionUF unionInstance;
	private boolean[] connectTop;
	private boolean[] connectBottom;

	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Illegal Argument");
		}
		this.n = n;
		gridLength = n * n + 1;
		unionInstance = new WeightedQuickUnionUF(gridLength);
		gridOpen = new boolean[gridLength];
		connectTop = new boolean[gridLength];
		connectBottom = new boolean[gridLength];
		isPercolated = false;
	}

	public void open(int row, int col) {
		if (isOpen(row, col)) {
			return;
		}
		int cellId = rc2id(row, col);
		gridOpen[cellId] = true;
		int[] dx = { -1, 0, 0, 1 };
		int[] dy = { 0, -1, 1, 0 };
		for (int i = 0; i < dy.length; i++) {
			int posX = row + dx[i];
			int posY = col + dy[i];
			if (posX == 1 && posY == 3) {
				System.out.println("here");
			}
			if (isPosValid(posX, posY) && isOpen(posX, posY)) {
				int posId = rc2id(posX, posY);
				if (connectTop[unionInstance.find(posId)]) {
					connectTop[unionInstance.find(cellId)] = true;
				}
				if (connectBottom[unionInstance.find(posId)]) {
					connectBottom[unionInstance.find(cellId)] = true;
				}
				unionInstance.union(cellId, posId);
			}
		}
		if (1 == row) {
			connectTop[unionInstance.find(cellId)] = true;
		} else if (n == row) {
			connectBottom[unionInstance.find(cellId)] = true;
		}
		if (connectTop[unionInstance.find(cellId)] && connectBottom[unionInstance.find(cellId)]) {
			isPercolated = true;
		}
	}

	private int rc2id(int row, int col) {
		return (row - 1) * n + col;
	}

	private void validateIJ(int row, int col) {
		if (!(row >= 1 && row <= n && col >= 1 && col <= n)) {
			throw new IndexOutOfBoundsException("Index is not betwwen 1 and N");
		}
	}

	private boolean isPosValid(int row, int col) {
		if (row >= 1 && row <= n && col >= 1 && col <= n) {
			return true;
		}
		return false;
	}

	public boolean isOpen(int row, int col) {
		validateIJ(row, col);
		return gridOpen[rc2id(row, col)];
	}

	public boolean isFull(int row, int col) {
		validateIJ(row, col);
		for (int i = 0; i < connectBottom.length; i++) {
			System.out.println(connectTop[i]);
		}
		
		return connectTop[unionInstance.find(rc2id(row, col))];
	}

	public int numberOfOpenSites() { // number of open sites
		int openNum = 0;
		for (int i = 1; i < gridLength - 1; i++) {
			if (gridOpen[i])
				openNum++;
		}
		return openNum;
	}

	public boolean percolates() {
		return isPercolated;
	}
	
//	public static void main(String[] args) {
//      Percolation p = new Percolation(3);
//      p.open(1, 3);
//      p.open(2, 3);
//      p.open(3, 3);
//      System.out.println(p.isFull(3, 3));
//	}
}
