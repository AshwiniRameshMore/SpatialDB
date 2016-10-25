
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class FrontPanel extends JPanel{
    private List<ShapeDetails> shapeDetailsList = new ArrayList<ShapeDetails>();                
    protected int YFactor = 7, XFactor = 10;
    Graphics display = null;
    public void refreshList(List<ShapeDetails> pList){
        this.shapeDetailsList = pList;
    }
    
    public void updateShapeList(Graphics g) {
        paintComponent(g);
    }
    
    protected void paintComponent(Graphics arg0) {
        super.paintComponent(arg0);
        display = arg0;
        for(int i=0;i<shapeDetailsList.size();i++){
            ShapeDetails content = shapeDetailsList.get(i);
        
            if("rectangle".equalsIgnoreCase(content.sType)){
                for(int j=0;j<content.sPoints.length;j+=4){
                    int x1 = (int)content.sPoints[j]*XFactor +20;
                    int y1 = (int)content.sPoints[j+1]*YFactor-400;
                    int x2 = (int)content.sPoints[j+2]*XFactor+20;
                    int y2 = (int)content.sPoints[j+3]*YFactor-400;
                    int minX = 0;
                    int minY = 0;
                    if(x1 > x2)
                    {
                     	minX = x2;
                        minY = y1;
                    }
                    else
                    {
                    	minX = x1;
                        minY = y2;
                    }
                    int rectHeight = Math.abs(y2-y1);
                    int rectWidth = Math.abs(x2-x1);
                    arg0.drawRect(minX, minY, rectWidth, rectHeight);
                }
            }
            else if("bird".equalsIgnoreCase(content.sType)){
            	float[] x = new float[(content.sPoints.length/2)];
            	float[] y = new float[(content.sPoints.length/2)];
                int xCount = 0;
                int yCount = 0;
            	
                for( int j=0;j<content.sPoints.length;j+=2){                        
                    x[xCount++] =(content.sPoints[j])*XFactor+20;
                    y[yCount++] =content.sPoints[j+1]*YFactor-400;
                }
                for(int k=0; k<xCount-1;k+=2)
                {
                Path2D.Double path = new Path2D.Double();
                path.moveTo((int)x[k],(int)y[k]);
                path.curveTo((int)x[k], (int)y[k], (int)x[k+1], (int)y[k+1], (int)x[k+2], (int)y[k+2]);
                Graphics2D gTmp = (Graphics2D) arg0;
                gTmp.draw(path);
                }
             }
            else if("arc".equalsIgnoreCase(content.sType)){
            	float[] x = new float[(content.sPoints.length/2)];
            	float[] y = new float[(content.sPoints.length/2)];
                int xCount = 0;
                int yCount = 0;
            	
                for( int j=0;j<content.sPoints.length;j+=2){                        
                    x[xCount++] =(content.sPoints[j])*XFactor+20;
                    y[yCount++] =content.sPoints[j+1]*YFactor-400;
                }
                for(int k=0; k<xCount-2;k+=3)
                {
                Path2D.Double path = new Path2D.Double();
                path.moveTo((int)x[k], (int)y[k]);
                path.curveTo((int)x[k], (int)y[k], (int)x[k+1], (int)y[k+1], (int)x[k+2], (int)y[k+2]);
                Graphics2D gTmp = (Graphics2D) arg0;
                gTmp.draw(path);
                }
             }
            else if("circle".equalsIgnoreCase(content.sType)){
                float[] x = new float[(content.sPoints.length/2)];
                float[] y = new float[(content.sPoints.length/2)];
                int xCount = 0, yCount = 0;
              for( int j=0;j<content.sPoints.length;j+=2)
              {   
            	  x[xCount] = (content.sPoints[j])*XFactor+20;
                  y[yCount] = content.sPoints[j+1]*YFactor-400;
                  xCount++;
                  yCount++;
              }
              
              float X = calculateH(x[0]+1, y[0], x[1], y[1], x[2], y[2]);
              float Y = (int) calculateK(x[0], y[0], x[1], y[1], x[2], y[2]);
                float radius =  (float)Math.sqrt((X-x[0])*(X-x[0]) + (Y-y[0])*(Y-y[0]));
                float cornerX = X-radius;
                float cornerY = Y-radius;
                arg0.drawOval((int)cornerX,(int)cornerY,(int)radius*2,(int)radius*2);  // x,y,w,h
            }
             else if("line".equalsIgnoreCase(content.sType))
             {                    
                float[] xArray = new float[(content.sPoints.length/2)];
                float[] yArray = new float[(content.sPoints.length/2)];
                int xCount = 0;
                int yCount = 0;
                for(int j=0;j<content.sPoints.length;j+=2)
                {                        
                    xArray[xCount++] = (content.sPoints[j])*XFactor+20;
                    yArray[yCount++] = content.sPoints[j+1]*YFactor-400;
                }
                for(int k=0; k<xCount-1; k+=1)
                	arg0.drawLine((int)xArray[k], (int)yArray[k], (int)xArray[k+1], (int)yArray[k+1]);
            }
        }
    }
    
    private float calculateH(float x1, float y1, float x2, float y2, float x3, float y3)
    {
        float num = (x2*x2+y2*y2)*y3 - (x3*x3+y3*y3)*y2 - ((x1*x1+y1*y1)*y3 - (x3*x3+y3*y3)*y1) + (x1*x1+y1*y1)*y2 - (x2*x2+y2*y2)*y1;
        float denom = (x2*y3-x3*y2) - (x1*y3-x3*y1) + (x1*y2-x2*y1);
        denom *= 2;
        return num / denom;
    }
    private float calculateK(float x1, float y1, float x2, float y2, float x3, float y3)
    {
        float num = x2*(x3*x3+y3*y3) - x3*(x2*x2+y2*y2) - (x1*(x3*x3+y3*y3) - x3*(x1*x1+y1*y1)) + x1*(x2*x2+y2*y2) - x2*(x1*x1+y1*y1);
        float denom = (x2*y3-x3*y2) - (x1*y3-x3*y1) + (x1*y2-x2*y1);
        denom *= 2;
        return num / denom;
    }
}