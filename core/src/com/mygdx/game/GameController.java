package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.awt.Point;
import java.awt.Rectangle;

public class GameController {

    /** Controller, to handle All gametasks, as:
     *  -Camera Views
     *  -Asset Loading
     */
    boolean loaded = false; //Makes the game controller wait untill game loaded

    public GameController(){
        //Preparing the Game view:
        prepareView();

        //Managing Assets:
        loadAssets();

        //Loads Main Game Objects:
        loadGameView_main();

        loaded = true;
    }

    //==================Rendering_Method:=====================================
    public void render_main(){
        if(!loaded)return;
        processTouchInput();
        main_cam.update();
        sb.setProjectionMatrix(main_cam.combined);
        background.update(sb); //To Do first to be drawn first.
        frontView.update(sb);
        scope.update(sb);
        bomb_controler.update(sb);
        player.update(sb);
        enemy_control.update(sb);
        score_manager.update(sb);

    }
    //=================Load-Game==============================================
    Player player;
    Background background;
    FrontView frontView; //front View
    Scope scope;
    Rectangle rect_front_view;//Collision rect, for the Front View
    public BombControl bomb_controler; //Contolls al the bomb dropping
    public EnemyControl enemy_control; //Conrolls all the enemys

    ScoreManager score_manager; //Manages and draws the Game-Score
    public void loadGameView_main(){
        frontView = new FrontView(this);
        player = new Player(this);
        background = new Background(this);

        scope = new Scope(this);
        rect_front_view = new Rectangle(((int)(getGameWidth()-2*frontView.width_tile)),0,((int)(frontView.width_tile*2)),(int)frontView.height_tile);
        bomb_controler = new BombControl(this );
        enemy_control = new EnemyControl(this);

        score_manager = new ScoreManager(this);
    }
    //=========================================================================

    SpriteBatch sb; //Camera view drawing Sprite Batch.
    OrthographicCamera main_cam; //Camera, that handels the Mail game view
    float width, height, ratio, size;
    GameInputHandler inputHandler; //handels touch and key inputs
    public void prepareView(){
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        ratio = width/height; //Screen Ratio for cross Device distribution

        size = 1000; // Leveling General height
        main_cam = new OrthographicCamera();
        main_cam.setToOrtho(false,size*ratio, size);

        sb = new SpriteBatch();
        sb.setProjectionMatrix(main_cam.combined);

        //Input Processing:
        inputHandler = new GameInputHandler(this);
        Gdx.input.setInputProcessor(inputHandler);

        //Prepare Front View #no its loaded in the game Main load
    }
    //=============================Method to Carry out Input :=============================
    public void processInput(String type, int[] values){

        if(type.equals("keyDown")){

            if(Input.Keys.SPACE==values[0])
                player.draw_weapon(true);
            if(Input.Keys.W==values[0])
                player.dir_y=2;
            if(Input.Keys.S==values[0])
                player.dir_y = 1;
            if(Input.Keys.D==values[0])
                player.dir_x = 2;
            if(Input.Keys.A==values[0])
                player.dir_x = 1;
        }

        if(type.equals("keyUp")){
            if(Input.Keys.SPACE==values[0])
                player.draw_weapon(false);
            if(Input.Keys.W==values[0])
                player.dir_y = 0;
            if(Input.Keys.S==values[0])
                player.dir_y = 0;
            if(Input.Keys.D==values[0])
                player.dir_x = 0;
            if(Input.Keys.A==values[0])
                player.dir_x = 0;
        }
    }

    float x_touch, y_touch;
    public void processTouchInput(){
        y_touch = getGameHeight()*((height - Gdx.input.getY())/height);
        x_touch = getGameWidth()*((Gdx.input.getX())/width);

        //Gdx.app.log("WOW"," X"+x_touch+" Y:"+y_touch);
        //Touch handeling:
        if(rect_front_view.contains(new Point((int)x_touch,(int)y_touch))){
            scope.x_draw = x_touch;
            scope.y_draw = y_touch;

            enemy_control.checkIfEnemyInScope(x_touch,y_touch,Gdx.input.isTouched());//To shoot enemys trough scope

            //Chaning Player position acordingly:
            //player.touched = true;
            player.y_hori = player.y_max*(((getGameWidth()-frontView.width_tile*2)-(getGameWidth()-x_touch))/(frontView.width_tile*2));
            //splayer.update(sb);
        }
    }
    //=====================================================================================

    AssetManager assetManager;
    String[] files_to_load = {"badlogic.jpg","game_assets.atlas"}; //All Assets to load
    public void loadAssets(){
        assetManager = new AssetManager();
        for( String s : files_to_load){
            //Gdx.app.log("WOW",s);
            if(s.substring(s.length()-4,s.length()).equals("tlas"))
                assetManager.load(s, TextureAtlas.class);
            else if(s.substring(s.length()-4,s.length()).equals(".jpg"))
                assetManager.load(s, Texture.class);
        }
        assetManager.finishLoading();
    }

    public TextureAtlas getAtlas(){
        return assetManager.get("game_assets.atlas");
    }

    public Texture getAssetT(String name){
        return assetManager.get(name);
    }

    public float getDelta(){//Time durration between frames.
        return Gdx.graphics.getDeltaTime();
    }

    public float getGameWidth(){
        return main_cam.viewportWidth;
    }

    public float getGameHeight(){
        return  main_cam.viewportHeight;
    }

}
