package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu {
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private Stage stage;
	private BitmapFont font;
	private TextField userField;
	private TextField ipField;
	private TextField portField;
	private boolean confirmed;
	
	public MainMenu() {
		initGraphics();
		confirmed = false;
	}
	
	public void draw() {
		stage.draw();
	}
	
	public String getName() {
		return userField.getText();
	}
	
	public String getIP() {
		return ipField.getText();
	}
	
	public String getPort() {
		return portField.getText();
	}
	
	public boolean isFinished() {
		return confirmed;
	}
	
	private void initGraphics() {
        font = new BitmapFont();
        initButton();
	}
	
	private void initButton() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		Skin skin = new Skin();
		Pixmap pixmap = new Pixmap(100, 100, Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fill();
 
		skin.add("white", new Texture(pixmap));
		TextFieldStyle textFieldStyle = new TextFieldStyle();
		textFieldStyle.font = font;
		textFieldStyle.fontColor = new Color(0, 0, 0, 1);
		
		textFieldStyle.background = skin.getDrawable("white");
		textFieldStyle.cursor = skin.newDrawable("white", Color.BLUE);
		textFieldStyle.cursor.setMinWidth(2);
		textFieldStyle.selection = skin.newDrawable("white", 0.5f, 0.5f, 0.5f, 0.5f);
		float y = 400;
		skin.add("meme", textFieldStyle);
		
		float yOff = 80;
		
		userField = new TextField("User Name", skin, "meme");
		userField.setMessageText("User name");
		userField.setPosition(170, y);
		
		ipField = new TextField("Remote IP", skin, "meme");
		ipField.setMessageText("Remote IP");
		ipField.setPosition(170, y - yOff);
		
		portField = new TextField("Port Field", skin, "meme");
		portField.setMessageText("Port field");
		portField.setPosition(170, y - yOff * 2);
		portField.setTextFieldFilter(new TextFieldFilter.DigitsOnlyFilter());
		stage.addActor(userField);
		stage.addActor(ipField);
		stage.addActor(portField);
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
 
		textButtonStyle.font = font;
		skin.add("default", textButtonStyle);
		
		TextButton confirmButton = new TextButton("Click me!", skin);
		confirmButton.setPosition(170, 215);
		confirmButton.setSize(300, 50);
		confirmButton.addListener( new ClickListener() {              
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
		    	System.out.println("CLICKED");
		        confirmed = true;
		    };
		});
		stage.addActor(confirmButton);
	}
}
