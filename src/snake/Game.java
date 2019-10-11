package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImagingOpException;
import java.util.Random;

public class Game extends JPanel implements ActionListener, MouseListener {


    private boolean inGame = false;
    private boolean status = false;

    //napisy ktore widnieja w menu
    private String PLAY = "PLAY", HIGHSCORE = "HIGHSCORE", EXIT="EXIT";


    private int highscore;

    //zmienne ktore sa potrzebne do lokalizacji napisow, po kliknieciu ich umozliwa przejsc do gry badz opuszczenie jej
    private int x1_play=130, x2_play=270, y1_play=60, y2_play=110, x1_highscore=50, x2_highscore=360, y1_highscore=161, y2_highscore=200,
            x1_exit=130, x2_exit=250, y1_exit=260, y2_exit=310;

    private final int width = 400;              //szerokosc okna gry
    private final int height = 400;             //wysokosc okna gry
    private final int movement_dot_size = 10;   //odleglosci miedzy komorkami w oknie
    private final int max_snake_size = 160;     //maksymalna dlugosc weza
    private final int Random_position = 40;     //zmienna, ktora przydaje sie podczas losowania pozycji itemow
    private int time_delay = 140;               //opoznienie Timera, ktory wyzwala actionPerformed

    private final int x[]= new int[max_snake_size]; //tablica w pozycji x, w ktorej znajduje sie waz
    private final int y[] = new int[max_snake_size];//tablica w pozycji y, w ktorej znajduje sie waz

    private int dots;                           //zmienna ktora odpowiada za dlugosc ogonoa
    private int level;

    //pozycje wszystkich item'ow
    private int apple_x, apple_y, Green_apple_x, Green_apple_y, Gold_apple_x, Gold_apple_y, DirectionImage_x, DirectionImage_y,
            smaller_x, smaller_y, bigger_x, bigger_y;

    //zmienne kierunkowe
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean changeDirection = false;

    private int change_speed = 0;           //zmienna ktora jest potrzebna do zmiany predkosci weza, zmiany zmiennej time_delay

    private Timer timer; //gra oparta jest na tej klasie

    private Image background, background2, background3,background4,body, head, apple, Green_apple, Gold_apple, DirectionImage, smaller, bigger;

    private ImageIcon bcg, bcg2, bcg3, bcg4, Icon_dot, Icon_apple, Icon_Green_apple, Icon_Gold_apple, Icon_head, Icon_head2, Icon_head3,
            Icon_direction, Icon_smaller, Icon_bigger;


    public Game(){
        addMouseListener(this);             //mozliwosc wyboru za pomoca myszki napisow w menu
        addKeyListener(new TAdapter());        //mozliwe sterowanie wezem za pomoca klawiszy
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(width, height));
    }

    private void loadImages() {

        bcg = new ImageIcon(this.getClass().getResource("bcg.png"));
        background = bcg.getImage();

        bcg2 = new ImageIcon(this.getClass().getResource("bcg2.jpg"));
        background2 = bcg2.getImage();

        bcg3 = new ImageIcon(this.getClass().getResource("bcg3.png"));
        background3 = bcg3.getImage();

        bcg4 = new ImageIcon(this.getClass().getResource("bcg4.jpg"));
        background4 = bcg4.getImage();

        Icon_dot = new ImageIcon(this.getClass().getResource("dot.png"));
        body = Icon_dot.getImage();

        Icon_apple = new ImageIcon(this.getClass().getResource("apple.png"));
        apple = Icon_apple.getImage();

        Icon_Green_apple = new ImageIcon(this.getClass().getResource("Green_apple.png"));
        Green_apple = Icon_Green_apple.getImage();

        Icon_Gold_apple = new ImageIcon(this.getClass().getResource("Gold_apple.png"));
        Gold_apple = Icon_Gold_apple.getImage();

        Icon_head = new ImageIcon(this.getClass().getResource("head.png"));
        Icon_head2 = new ImageIcon(this.getClass().getResource("head2.png"));
        Icon_head3 = new ImageIcon(this.getClass().getResource("head3.png"));
        head = Icon_head.getImage();

        Icon_direction = new ImageIcon(this.getClass().getResource("Direction.png"));
        DirectionImage = Icon_direction.getImage();

        Icon_smaller = new ImageIcon(this.getClass().getResource("smaller.png"));
        smaller = Icon_smaller.getImage();

        Icon_bigger = new ImageIcon(this.getClass().getResource("bigger.png"));
        bigger = Icon_bigger.getImage();
    }

    public void initGame(){
        dots = 3;

        //miejsce startowe
        for (int z = 0; z <dots; z++){
            x[z]= 50 - z*10;
            y[z] = 50;
        }

        //lokalizacja jablka
        locateApples(apple_x,apple_y);

        timer = new Timer(time_delay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(status == true){
            doDrawing(g);
        }
        else{
            MenuDrawing(g);
        }
    }

    public void doDrawing(Graphics g) {

        if(dots<6){
            level = 1;
            timer.stop();
            time_delay=140;
            timer=new Timer(time_delay, this);
            timer.start();
        }
        if(dots>5 && dots<10)
        {
            level = 2;
            g.drawImage(background2,0,0,400,400,this);
            timer.stop();
            time_delay=100;
            timer = new Timer(time_delay, this);
            timer.start();
        }
        if(dots>9 && dots<17){
            level = 3;
            g.drawImage(background3, 0,0 ,400,400,this);
            timer.stop();
            if(change_speed==-1){
                time_delay=100;
            }else if(change_speed==1){
                time_delay=30;
            }else{
                time_delay=60;
            }
            timer=new Timer(time_delay, this);
            timer.start();
        }
        if(dots>16){
            level = 4;
            g.drawImage(background4, 0,0 ,400,400,this);
            timer.stop();
            if(change_speed==-1){
                time_delay=60;
            }else if(change_speed==1){
                time_delay=15;
            }else{
                time_delay=30;
            }
            timer=new Timer(time_delay, this);
            timer.start();
        }

        if(inGame) {
            highscore = dots - 3;
            StatusGame(g, "SCORE: " + highscore, 345, 25, 10, Color.RED);
            StatusGame(g, "LEVEL: " + level, 345, 15, 10, Color.RED);

            g.drawImage(apple, apple_x,apple_y,this);

            if(dots>5){
                g.drawImage(Green_apple, Green_apple_x,Green_apple_y, this);
            }
            if(changeDirection==true){
                g.drawImage(Gold_apple, Gold_apple_x, Gold_apple_y, this);
            }
            if(dots>9){
                if(changeDirection==false){
                    g.drawImage(DirectionImage, DirectionImage_x, DirectionImage_y, this );
                }
            }
            if (dots > 13) {
                if(change_speed==0 || change_speed==1){
                    g.drawImage(smaller, smaller_x, smaller_y, this);
                }
                if(change_speed==-1 || change_speed==0){
                    g.drawImage(bigger, bigger_x, bigger_y,this);
                }
            }

            //rysowanie weza
            for(int z=0; z<dots; z++){
                if(z==0){
                    g.drawImage(head, x[z], y[z],this);
                }else{
                    g.drawImage(body, x[z], y[z],this);
                }
            }

        }else if(inGame==false&&status==true){
            g.drawImage(background, 0,0 ,400,400,this);
            StatusGame(g, "Game Over", 10, 200, 70, Color.RED);
        }
        else{
            MenuDrawing(g);
        }
    }

    public void StatusGame(Graphics g, String level, int StatusGame_x, int StatusGame_y, int StatusGame_stringsize, Color color) {
        Font small = new Font("Helvetica", Font.BOLD, StatusGame_stringsize);
        g.setColor(color);
        g.setFont(small);
        g.drawString(level,StatusGame_x, StatusGame_y);
    }

//METODY CHECK SPRAWDZAJA CZY GLOWA WEZA ZNAJDUJE SIE W TEJ SAMEJ POZYCJI CO DANY ITEM
    public void checkApple(){
        if((x[0]==apple_x)&&(y[0]==apple_y)){
            changeDirection = false;
            dots++;
            head = Icon_head2.getImage();
            //locateApple();
            locateApples(apple_x, apple_y);
        }
    }

    public void checkGreen_apple(){
        if((x[0]==Green_apple_x)&&(y[0]==Green_apple_y)){
            change_speed=0;
            dots--;
            head = Icon_head3.getImage();
            //  locateGreenApple();
            locateApples(Green_apple_x, Green_apple_y);
        }
    }

    public void checkGold_apple(){
        if((x[0]==Gold_apple_x)&&(y[0]==Gold_apple_y)){
            changeDirection = false;
            change_speed=-1;
            dots+=2;
            head = Icon_head2.getImage();
            locateApples(Gold_apple_x, Gold_apple_y);
        }
    }

    public void checkDirectionImage(){
        if((x[0]==DirectionImage_x)&&(y[0]==DirectionImage_y)){
            changeDirection = true;
            head = Icon_head3.getImage();
            locateApples(DirectionImage_x, DirectionImage_y);
        }
    }

    public void check_smaller(){
        if((x[0]==smaller_x)&&(y[0]==smaller_y)){
            change_speed=-1;
            head = Icon_head2.getImage();
            locateApples(smaller_x, smaller_y);
        }
    }

    public void check_bigger(){
        if((x[0]==bigger_x)&&(y[0]==bigger_y)){
            change_speed=1;
            head = Icon_head3.getImage();
            locateApples(bigger_x, bigger_y);
        }
    }

    public void move(){
        //ogon
        for(int z = dots; z>0; z--){
            x[z]=x[z-1];
            y[z]=y[z-1];
        }

        if(leftDirection){
            if(changeDirection==false) {
                x[0] -= movement_dot_size;
            }else{
                x[0] +=movement_dot_size;
            }
        }

        if(rightDirection){
            if(changeDirection==false) {
                x[0] += movement_dot_size;
            }else{
                x[0] -=movement_dot_size;
            }
        }

        if(upDirection){
            y[0]-=movement_dot_size;
        }

        if(downDirection){
            y[0]+=movement_dot_size;
        }
    }

    public void checkCollision(){
        if(changeDirection==false) {
            for (int z = dots; z > 0; z--) {
                if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                    inGame = false;
                }
            }
        }
        if(y[0]>=height){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(x[0]>=width){
            inGame=false;
        }
        if(x[0]<0){
            inGame=false;
        }
        if(!inGame&&status==true){
            timer.stop();
        }
    }
//te metody wtedy jablko zostaje w miejscu gdzie bylo
    /*private void locateApple() {
        int r = (int) (Math.random()* Random_position);
        apple_x=r*movement_dot_size;

        r=(int)(Math.random()*Random_position);
        apple_y = r *movement_dot_size;
    }

   private void locateGreenApple() {
        int r = (int) (Math.random()* Random_position);
        Green_apple_x=r*movement_dot_size;

        r=(int)(Math.random()*Random_position);
        Green_apple_y= r *movement_dot_size;
    }*/

    public void locateApples(int x, int y) {

        int r;

        if(x==apple_x&& y==apple_y);
        {
            r = (int) (Math.random()* Random_position);
            apple_x = r * movement_dot_size;            //zeby waz pokrywal sie z pozycja jablek
            r = (int) (Math.random()* Random_position);
            apple_y = r * movement_dot_size;
        }

        if(x==Green_apple_x && y==Green_apple_y);
        {
            r = (int) (Math.random()* Random_position);
            Green_apple_x = r * movement_dot_size;
            r = (int) (Math.random()* Random_position);
            Green_apple_y = r * movement_dot_size;
        }

        if(x==Gold_apple_x && y==Gold_apple_y);
        {
            r = (int) (Math.random()* Random_position);
            Gold_apple_x = r * movement_dot_size;
            r = (int) (Math.random()* Random_position);
            Gold_apple_y = r * movement_dot_size;
        }

        if(x==DirectionImage_x && y==DirectionImage_y);
        {
            r = (int) (Math.random()* Random_position);
            DirectionImage_x = r * movement_dot_size;
            r = (int) (Math.random()* Random_position);
            DirectionImage_y = r * movement_dot_size;
        }

        if(x==smaller_x && y==smaller_y);
        {
            r = (int) (Math.random()* Random_position);
            smaller_x = r * movement_dot_size;
            r = (int) (Math.random()* Random_position);
            smaller_y = r * movement_dot_size;
        }

        if(x==bigger_x && y==bigger_y);
        {
            r = (int) (Math.random()* Random_position);
            bigger_x = r * movement_dot_size;
            r = (int) (Math.random()* Random_position);
            bigger_y = r * movement_dot_size;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            head = Icon_head.getImage();
            checkApple();
            if(dots>5){
                checkGreen_apple();
            }
            if(changeDirection==true){
                checkGold_apple();
            }
            if(dots>9){
                checkDirectionImage();
            }
            if(dots>13){
                check_smaller();
                check_bigger();
            }

            checkCollision();
            move();
        }

        repaint();
    }

    public void MenuDrawing(Graphics g) {
        Font font = new Font("arial", Font.BOLD, 50);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(PLAY, 130,100);
   //   g.drawString(HIGHSCORE, 50,200);
        g.drawString(EXIT, 135,300);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getX()>x1_play&&e.getX()<x2_play&&e.getY()>y1_play&&e.getY()<y2_play){
            status=true;
            inGame=true;
            loadImages();
            initGame();
        }
      /*  else if(e.getX()>x1_highscore&&e.getX()<x2_highscore&&e.getY()>y1_highscore&&e.getY()<y2_highscore){
            System.out.println(highscore);
        }*/
        else if(e.getX()>x1_exit&&e.getX()<x2_exit&&e.getY()>y1_exit&&e.getY()<y2_exit){
            System.exit(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if(key == KeyEvent.VK_LEFT && !rightDirection){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if(key == KeyEvent.VK_RIGHT && !leftDirection){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if(key == KeyEvent.VK_UP && !downDirection){
                upDirection = true;
                leftDirection = false;
                rightDirection = false;

            }

            if(key == KeyEvent.VK_DOWN && !upDirection){
                downDirection = true;
                leftDirection = false;
                rightDirection = false;

            }

        }
    }
}

