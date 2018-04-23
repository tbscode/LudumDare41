package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BasicEnemy {
    //Will at first use the same Animation, as the main Player.

    //Debug:
    boolean draw_hitbox = false;


    //The Right screen debt will be 1080
    GameController control;

    /** Enemys Entetys **/
    float x_pos = 710, y_pos = 0; //Between:
    float width = 400, height = 400; //Smallest 80*80 Biggest: 400*400 fiff:320
    float size_decrease_max = 320;
    float y_move_speed;
    float y_visibility_height = 10;// Determines when the enemy enters the side view
    float beginning_left_pane = 0;

    float height_pane = 143;


    float animation_speed= 0.2f;

    Rectangle hitBox; //The Enemys Hitbox
    float hitbox_scale_x = 0.17f;
    float hitbox_width = 0; //*width

    Rectangle second_hitbox;
    float scale_x = 0.3f;
    float scale_y = 0.08f;

    public BasicEnemy(GameController controller, float x_start, float y_speed){
        this.control = controller;

        this.x_pos = x_start;
        this.y_move_speed = y_speed;

        beginning_left_pane = controller.getGameWidth()-controller.frontView.width_tile*2;

        //load Enemy Assets:
        loadAssets();
    }

    TextureAtlas player_atlas;
    TextureRegion frame_1_A,frame_2_A,frame_3_A,frame_4_A,frame_5_A;
    TextureRegion[]frames;
    Animation walk;
    float animationTimer = 0;
    TextureRegion currenFrame;

    ShapeRenderer renderer;//can render the hitboxes
    public void loadAssets(){
        player_atlas = control.getAtlas();
        // Normal Walk Frames:
        frame_1_A = player_atlas.findRegion("frame_1_A");
        frame_2_A = player_atlas.findRegion("frame_2_A");
        frame_3_A = player_atlas.findRegion("frame_3_A");//Idle frame
        frame_4_A = player_atlas.findRegion("frame_4_A");
        frame_5_A = player_atlas.findRegion("frame_5_A");

        currenFrame = frame_3_A;

        frames = new TextureRegion[]{frame_1_A,frame_2_A,frame_3_A,frame_4_A,frame_5_A};
        walk = new Animation(animation_speed,frames);
        walk.setPlayMode(Animation.PlayMode.LOOP);
        hitBox = new Rectangle();
        second_hitbox = new Rectangle();

        renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(control.main_cam.combined);
    }

    public boolean inLeftView = false;
    public void enterNewView(){
        inLeftView = true;
        x_on_2D_pos= control.getGameWidth()-control.frontView.width_tile*2;
    }

    //Draws the enemy, when he enters the 2D side view
    float x_on_2D_pos,y_on_2D_pos;
    float left_height = 330;
    TextureRegion currenFrame_side;
    float enemy_height_side = 350, enemy_width_side = 350; //The default values
    float height_flat,width_flat;
    public void newViewDraw(SpriteBatch sb){
        currenFrame_side = (TextureRegion) walk.getKeyFrame(animationTimer);
        if(!currenFrame_side.isFlipX())currenFrame_side.flip(true,false);
        y_on_2D_pos = 330*(x_pos/1410);
        update_dimensionsSide();
        sb.begin();
        sb.draw(currenFrame,x_on_2D_pos-width_flat,y_on_2D_pos,width_flat,height_flat);
        second_hitbox.set((x_on_2D_pos-width_flat)  +width_flat/2,y_on_2D_pos,width_flat*scale_x,height_flat*scale_y);
        //Gdx.app.log("WOW","X: "+x_on_2D_pos+"Y"+y_on_2D_pos);
        sb.end();
    }

    float scale = 0;
    public void update_dimensionsSide(){
        scale = y_on_2D_pos/350;

        height_flat = enemy_height_side - 150*scale;
        width_flat = enemy_width_side -150*scale;

    }

    Vector2 new_pos;

    public void update(SpriteBatch sb){
        animationTimer+= control.getDelta();
        currenFrame = (TextureRegion) walk.getKeyFrame(animationTimer);
        if(x_pos-705 +width/2< 0){
            if(!currenFrame.isFlipX()) {
                currenFrame.flip(true, false);
            }
        }else if(x_pos-705 +width/2 > 0){
            if(currenFrame.isFlipX())
                currenFrame.flip(true,false);
        }
        randomMovement();
        new_pos = control.frontView.Project_to_game_space(x_pos,y_pos);
        //Gdx.app.log("POS"," X_N: "+new_pos.x+"Y_N: "+new_pos.y);//Re renders the position and moves the view
        if(new_pos.y < y_visibility_height&&!inLeftView){//Now the Enemy Enters the Players 2D View.
            enterNewView();
        }
        draw(sb);

        if(draw_hitbox) {
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.rect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
            renderer.rect(second_hitbox.x,second_hitbox.y,second_hitbox.width,second_hitbox.height);
            renderer.end();
        }

    }

    public void draw(SpriteBatch sb){
        if(inLeftView){
            newViewDraw(sb);
        }
        sb.begin();




        hitBox.set(new_pos.x+(control.getGameWidth()-control.frontView.width_tile*2),new_pos.y,
                (width-size_decrease_max*sizeMultiply(new_pos.y)),height-size_decrease_max*sizeMultiply(new_pos.y));

        //Its beeing rendert in the midde of the sprite square:
        sb.draw(currenFrame,hitBox.x,hitBox.y,hitBox.width,hitBox.height);

                //width-size_decrease_max*sizeMultiply(new_pos.y),height-size_decrease_max*sizeMultiply(new_pos.y));
                //width-size_decrease_max*sizeMultiply(new_pos.y),height-size_decrease_max*sizeMultiply(new_pos.y));
        hitbox_width = hitBox.width*hitbox_scale_x;
        hitBox.setX(hitBox.x+hitBox.width/2 -hitbox_width/2);
        hitBox.setWidth(hitbox_width);
        sb.end();
    }


    public void randomMovement(){
        //Always moves forward and randomly, to left and right
        y_pos += y_move_speed; // Not so random ;) for the forward movement speed
        x_pos = (float) (2-Math.random()*4)+x_pos;

        if(inLeftView)
            x_on_2D_pos-=y_move_speed;


    }

    public boolean isInScope(float x_scope,float y_scope){
        if(hitBox.contains(new Vector2(x_scope,y_scope))){
            return true;
        } else{
            return false;
        }
    }




    public float sizeMultiply(float y){
        return y/height_pane;
    }
    public boolean dead = false;
    public void gotShot(){ //When Player aimed and shot at this Enemy
        dead = true;
        control.score_manager.increase(control.score_manager.normal_kill);
        control.enemy_control.requestRemove(this);
    }
}
