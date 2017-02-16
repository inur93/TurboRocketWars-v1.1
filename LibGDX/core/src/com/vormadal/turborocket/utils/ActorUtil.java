package com.vormadal.turborocket.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.badlogic.gdx.utils.Array;

public class ActorUtil {

	public static Vector2 getSize(Body body){
		Array<Fixture> fixtures = body.getFixtureList();


		float width = 0;
		float height = 0;
		float widthLow = 0, widthHigh = 0;
		float heightLow = 0, heightHigh = 0;
		boolean first = true;
		for(Fixture f : fixtures){
			Type t = f.getType();
			switch (t) {
			case Chain:

				break;
			case Polygon:
				PolygonShape poly = (PolygonShape) f.getShape();
				for(int i = 0; i < poly.getVertexCount(); i++){
					Vector2 point = new Vector2();
					poly.getVertex(i, point);
					if(first){
						widthHigh = point.x;
						widthLow = point.x;
						heightLow = point.y;
						heightHigh = point.y;
						first = false;
					}else{
						if(widthHigh < point.x) widthHigh = point.x;
						if(widthLow > point.x) widthLow = point.x;
						if(heightHigh < point.y) heightHigh = point.y;
						if(heightLow > point.y) heightLow = point.y;
					}
				}				
				break;
			case Circle:

				break;
			case Edge:

				break;
			default:
				break;
			}
		}
		width = widthHigh-widthLow;
		height = heightHigh-heightLow;
		return new Vector2(width, height);
	}
}
