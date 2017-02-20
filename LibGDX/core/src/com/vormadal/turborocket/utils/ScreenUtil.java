package com.vormadal.turborocket.utils;

public class ScreenUtil {

	public static ScreenData[] getScreenData(int numPlayers, float screenWidth, float screenHeight){
		ScreenData[] data = new ScreenData[numPlayers];
		for(int i = 0; i < numPlayers; i++){
			float posx = i*screenWidth/numPlayers + (i%1 == 0 ? -screenWidth/(numPlayers*2) : screenWidth/(numPlayers*2));
			float posy = 0;
			float width = screenWidth/numPlayers;
			float height = screenHeight;
			
			data[i] = new ScreenData(posx, posy, width, height);
		}
		return data;
	}
	
	
}
