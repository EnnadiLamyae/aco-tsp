package org.aco.tsp.visualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.aco.tsp.config.Edge;
import org.aco.tsp.config.Node;

public class Visualizer extends JFrame {

	int viewWidth;
    int viewHeight;
    int width;
    int height;
    double scaleW;
    double scaleH;

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    double[][] coordinates;

    private JLabel stats;

    public Visualizer(double[][] coordinates) {
        super();
        this.coordinates = coordinates;
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.stats = new JLabel();
        this.pack();
        this.setLocationRelativeTo(null);
        this.add(stats, BorderLayout.SOUTH);
        this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        this.getContentPane().setBackground(Color.black);
        this.setVisible(true);
        try { Thread.sleep(1500); } catch (Exception ex) {}
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        viewWidth = this.getWidth();
        viewHeight = this.getHeight();
        width = 4;
        height = 4;
        for(int i = 0; i < coordinates.length; i++) {
            if(coordinates[i][0] > scaleW) scaleW = (int) coordinates[i][0];
            if(coordinates[i][1] > scaleH) scaleH = (int) coordinates[i][1];
        }
        scaleW = viewWidth / scaleW;
        scaleH = viewHeight / scaleH;
        scaleW *= .9;
        scaleH *= .9;
    }

    public void draw(int[] tour) {
        this.nodes.clear();
        this.edges.clear();
        for(int i = 0; i < coordinates.length; i++) {
            int x = (int) (coordinates[i][0] * scaleW);
            int y = (int) (coordinates[i][1] * scaleH);
            this.addNode(String.valueOf(i), x, y);
        }
        for(int i = 0; i < tour.length - 1; i++) {
            this.addEdge(tour[i], tour[i + 1]);
        }
        this.repaint();
    }

    public void setStat(String text) {
        this.stats.setText(text);
    }

    // Add a node at pixel (x,y)
    public void addNode(String name, int x, int y) {
        nodes.add(new Node(name,x,y));
    }

    // Add an edge between nodes i and j
    public void addEdge(int i, int j) {
        edges.add(new Edge(i,j));
    }

    // Clear and repaint the nodes and edges
    public void paint(Graphics g) {
        super.paint(g);
        FontMetrics f = g.getFontMetrics();
        int nodeHeight = Math.max(height, f.getHeight());
        g.setColor(Color.green);
        
        try {
        	for (Edge e : this.edges) {
                g.drawLine(nodes.get(e.i).x, nodes.get(e.i).y, nodes.get(e.j).x, nodes.get(e.j).y);
            }
            for (Node n : nodes) {
                int nodeWidth = Math.max(width, f.stringWidth(n.name)+width);
                g.setColor(Color.white);
                g.fillRect(n.x-nodeWidth/2, n.y-nodeHeight/2, nodeWidth, nodeHeight);
                g.setColor(Color.black);
                g.drawRect(n.x-nodeWidth/2, n.y-nodeHeight/2, nodeWidth, nodeHeight);
                g.drawString(n.name, n.x-f.stringWidth(n.name)/2,n.y+f.getHeight()/2);
            }
		} catch (NullPointerException e) {
			e.getMessage();
		}
    }
}
