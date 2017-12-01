import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
	private Picture pic;
	private DirectedEdge[] edgeTo;
	private double[] distTo;
	private Picture antiCounterPic;
	private boolean isRotated;
   public SeamCarver(Picture picture) {
	   // create a seam carver object based on the given picture
	   if (picture == null) throw new java.lang.IllegalArgumentException();
	   this.pic = picture;
   }
   
   private Picture createAntiCounterPic(Picture picture) {
	   Picture newPic = new Picture(height(), width());
	   for (int i = 0; i < width(); i++) {
		   for (int j = 0; j < height(); j++) {
			   newPic.setRGB(j, width() - 1 - i, picture.getRGB(i, j));
		   }
	   }
	   return newPic;
   }
   
   private EdgeWeightedDigraph createGraph(Picture picture) {
	   EdgeWeightedDigraph newGraph = new EdgeWeightedDigraph(picture.width() * picture.height() +2);
	   for (int i = 0; i < picture.width() * (picture.height() - 1); i++) {
		   for (int j = -1; j < 2; j++) {
			   if ((i % picture.width()== 0 && j == -1) || (i % picture.width() == picture.width() - 1 && j == 1)) {
				   continue;
			   }
			   int next = i + picture.width() + j;
			   double energ = energy(i % picture.width(), i / picture.width());
			   DirectedEdge newEdge = new DirectedEdge(i, next, energ);
			   newGraph.addEdge(newEdge);
		   }
	   }
	   for (int i = 0; i < picture.width(); i++) {
		   DirectedEdge startEdge = new DirectedEdge(width() * height(), i, 0);
		   int lastRowVert = i + picture.width() * (picture.height() - 1);
		   double energ = energy(lastRowVert % picture.width(), lastRowVert / picture.width());
		   DirectedEdge endEdge = new DirectedEdge(lastRowVert, width() * height() + 1, energ);
		   newGraph.addEdge(startEdge);
		   newGraph.addEdge(endEdge);
	   }
	   return newGraph;
   }
   
   private void relax(DirectedEdge e) {
	   int v = e.from();
	   int w = e.to();
	   if (distTo[v] + e.weight() < distTo[w]) {
		   distTo[w] = distTo[v] + e.weight();
		   edgeTo[w] = e;
	   }
   }
   
   public double energy(int x, int y) {
	   Picture p;
	   if (isRotated == false) {
		   p = pic;
	   }
	   else p = antiCounterPic;
	   if (x < 0 || x > p.width() - 1 || y < 0 || y > p.height() - 1) {
		   throw new java.lang.IllegalArgumentException();
	   }
	   if (x==0 || x==p.width()-1 || y ==0 || y==p.height()-1) {
		   return 1000;
	   }
	   double ener = Math.sqrt(getGrad(x-1,y,x+1,y, p) + getGrad(x, y-1, x, y+1, p));
	   return ener;
   }
   
   private double getGrad(int x1, int y1, int x2, int y2, Picture p) {
	   int [] rgb1 = separateRGB(p.getRGB(x1, y1));
	   int [] rgb2 = separateRGB(p.getRGB(x2, y2));
	   double grad = (rgb1[0] - rgb2[0]) * (rgb1[0] - rgb2[0]) + (rgb1[1] - rgb2[1]) * (rgb1[1] - rgb2[1]) + (rgb1[2] - rgb2[2]) * (rgb1[2] - rgb2[2]);
	   return grad;
   }
   
   private int[] separateRGB(int rgb) {
	   int r = (rgb >> 16) & 0xFF;
	   int g = (rgb >>  8) & 0xFF;
	   int b = (rgb >>  0) & 0xFF;
	   int[] res = new int[] {r, g, b};
	   return res;
   }
   
   public Picture picture() {
	   // current picture
	   Picture returnPic = new Picture(pic);
	   return returnPic;
   }
   public int width() {
	   // width of current picture
	   return this.pic.width();
   }
   public int height() {
	   // height of current picture
	   return this.pic.height();
   }

   public   int[] findHorizontalSeam() {
	   // sequence of indices for horizontal seam
	   isRotated = true;
	   antiCounterPic = createAntiCounterPic(pic);
	   EdgeWeightedDigraph antiCounterGraph = createGraph(antiCounterPic);
	   int[] seam = findSeam(antiCounterGraph, antiCounterPic);
	   for (int i = 0; i < seam.length / 2; i++) {
		   int temp = seam[i];
		   seam[i] = seam[seam.length - 1 - i];
		   seam[seam.length - 1 - i] = temp;
		 }
	   isRotated = false;
	   return seam;
   }
   public   int[] findVerticalSeam() {
	   // sequence of indices for vertical seam
	   isRotated = false;
	   EdgeWeightedDigraph graph = createGraph(pic);
	   return findSeam(graph, pic);
   }
   
   private void initDistTo() {
	   distTo = new double[pic.width() * pic.height() + 2];
	   for (int i = 0; i < pic.width() * pic.height() + 2; i++) {
		   distTo[i] = Double.MAX_VALUE;
	   }
	   distTo[pic.width() * pic.height()] = 0;
	   edgeTo = new DirectedEdge[pic.width() * pic.height() +2];
   }
   
   private   int[] findSeam(EdgeWeightedDigraph g, Picture p) {
	   initDistTo();
	   int[] seam = new int[p.height()];
	   for (DirectedEdge e: g.adj(width() * height())) {
		   relax(e);
	   }
	   for (int i = 0; i < width() * height(); i++) {
		   for (DirectedEdge e: g.adj(i)) {
			   relax(e);
		   }
	   }
	   //relax the edges;
	   int c = p.height() - 1;
	   for (int vert = g.V() - 1; edgeTo[vert].from() != g.V()-2; vert = edgeTo[vert].from()) {
		   seam[c--] = edgeTo[vert].from() % p.width();
	   }
	   return seam;
   }
   
   public    void removeHorizontalSeam(int[] seam) {
	   // remove horizontal seam from current picture
	   if (seam == null) throw new java.lang.IllegalArgumentException();
	   if (seam.length != width() || seam.length <= 0) throw new java.lang.IllegalArgumentException();
	   for (int i = 1; i < seam.length; i++) {
		   if (Math.abs(seam[i] - seam[i-1]) >= 2) {
			   throw new java.lang.IllegalArgumentException();
		   }
	   }
	   if (height() == 1) throw new java.lang.IllegalArgumentException();
	   Picture newPic = removeSeamH(seam, pic);
	   pic = newPic;
   }
   public    void removeVerticalSeam(int[] seam) {
	   // remove vertical seam from current picture
	   if (seam == null) throw new java.lang.IllegalArgumentException();
	   if (seam.length != height() || seam.length <= 0) throw new java.lang.IllegalArgumentException();
	   for (int i = 1; i < seam.length; i++) {
		   if (Math.abs(seam[i] - seam[i-1]) >= 2) {
			   throw new java.lang.IllegalArgumentException();
		   }
	   }
	   if (width() == 1) throw new java.lang.IllegalArgumentException();
	   Picture newPic = removeSeamV(seam, pic);
	   pic = newPic;
   }
   
   private    Picture removeSeamV(int[] seam, Picture p) {
	   Picture newPic = new Picture(p.width() - 1, p.height());
	   int newX = 0;
	   System.out.println(p.width());
	   for (int y = 0; y < p.height(); y++) {
		   for (int x = 0; x < p.width(); x++) {
			   if (x == seam[y]) {
				   continue;
			   }
			   newPic.setRGB(newX, y, p.getRGB(x, y));
			   newX++;  
		   }
		   newX = 0;
	   }
	   return newPic;
   }
   
   private   Picture removeSeamH(int[] seam, Picture p) {
	   Picture newPic = new Picture(p.width(), p.height() - 1);
	   int newY = 0;
	   System.out.println(p.height());
	   for (int x = 0; x < p.width(); x++) {
		   for (int y = 0; y < p.height(); y++) {
			   if (y == seam[x]) {
				   continue;
			   }
			   newPic.setRGB(x, newY, p.getRGB(x, y));
			   newY++;
		   }
		   newY = 0;
	   }
	   return newPic;
   }
   
   public static void main(String[] args) {
	   
	   Picture pic1 = new Picture("/Users/xuzhang/Desktop/Algorithm_part2/week2/A2/seam/HJocean.png");
	   SeamCarver sc = new SeamCarver(pic1);
	   sc.pic.show();
	   for (int i = 0; i < 200; i++) {
		   int[] seamV = sc.findVerticalSeam();
		   sc.removeVerticalSeam(seamV);
	   }
	   sc.pic.show();
	   System.out.println("complete verticle");
	   for (int i = 0; i < 200; i++) {
		   int[] seamH = sc.findHorizontalSeam();
		   sc.removeHorizontalSeam(seamH);;
	   }
	   sc.pic.show();

	   
	   
   }
}



