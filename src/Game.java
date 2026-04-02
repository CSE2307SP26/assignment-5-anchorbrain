import java.awt.Color;
import java.awt.event.KeyEvent;

import edu.princeton.cs.introcs.StdDraw;

public class Game {

	public static void main(String[] args) {
		double lower_bound = 0.005;
		double upper_bound = 0.01;
		int ball_count = 3;
		double radius = 0.025;
		int score = 0;
		int high_score = 0;
		double player_x = 0.5;
		double player_y = 0.5;
		double player_speed = 0.01;
		double[] ball_x_vals = new double[ball_count];
		double[] ball_y_vals = new double[ball_count];
		double[] ball_x_vals_bounded = new double[ball_count];
		double[] ball_y_vals_bounded = new double[ball_count];
		
		for(int i = 0; i < ball_count; i++) {
			ball_x_vals[i] = Math.random();
			ball_y_vals[i] = Math.random();
			ball_x_vals_bounded[i] = Math.random() * (upper_bound - lower_bound) + lower_bound;
			ball_y_vals_bounded[i] = Math.random() * (upper_bound - lower_bound) + lower_bound;
		}
		
		StdDraw.enableDoubleBuffering();
		
		long score_timer = System.currentTimeMillis();
		long round_time = System.currentTimeMillis();
		
		while (true) {
			
			StdDraw.clear();
			boolean player_collision = false;
			for (int i = 0; i < ball_count; i++) {
				ball_x_vals[i] = ball_x_vals[i] + ball_x_vals_bounded[i];
				ball_y_vals[i] = ball_y_vals[i] + ball_y_vals_bounded[i];
				if (ball_x_vals[i] + radius > 1 || ball_x_vals[i] - radius < 0) { 
					ball_x_vals_bounded[i] = -ball_x_vals_bounded[i];
				}
				if (ball_y_vals[i] + radius > 1 || ball_y_vals[i] - radius < 0) { 
					ball_y_vals_bounded[i] = -ball_y_vals_bounded[i];
				}
				for (int j = 0; j < ball_count; j++) {
					if(i != j) {
						double distance_to_ball = Math.sqrt(Math.pow(ball_x_vals[i] - ball_x_vals[j], 2) + Math.pow(ball_y_vals[i] - ball_y_vals[j], 2));
						if(distance_to_ball < 2 * radius) {
							ball_x_vals_bounded[i] = -ball_x_vals_bounded[i];
							ball_y_vals_bounded[i] = -ball_y_vals_bounded[i];
						}
					}
				}
				
				double distance_to_player = Math.sqrt(Math.pow(ball_x_vals[i] - player_x, 2) + Math.pow(ball_y_vals[i] - player_y, 2));
				if (distance_to_player < 2 * radius) {
					player_collision = true;
				}
			}
			
			if (player_collision) {
				ball_count = 3;
				for(int i = 0; i < ball_count; i++) {
					ball_x_vals[i] = Math.random();
					ball_y_vals[i] = Math.random();
					ball_x_vals_bounded[i] = Math.random() * (upper_bound - lower_bound) + lower_bound;
					ball_y_vals_bounded[i] = Math.random() * (upper_bound - lower_bound) + lower_bound;
					score = 0;
					score_timer = System.currentTimeMillis();
					round_time = System.currentTimeMillis();
					player_x = 0.5;
					player_y = 0.5;
				}
				
				
			}
			
			if(StdDraw.isKeyPressed(KeyEvent.VK_W)) {
				player_y = player_y + player_speed;
			}
			if(StdDraw.isKeyPressed(KeyEvent.VK_S)) {
				player_y = player_y - player_speed;
			}
			if(StdDraw.isKeyPressed(KeyEvent.VK_A)) {
				player_x = player_x - player_speed;
			}
			if(StdDraw.isKeyPressed(KeyEvent.VK_D)) {
				player_x = player_x + player_speed;
			}
			
			if(player_x > 1) {
				player_x = 1;
			}
			if(player_x < 0) {
				player_x = 0;
			}
			if(player_y > 1) {
				player_y = 1;
			}
			if(player_y < 0) {
				player_y = 0;
			}
			
			long now = System.currentTimeMillis();
			if(now > score_timer + 1000) {
				score++;
				if(score > high_score) {
					high_score = score;
				}
				score_timer = now;
			}
			
			if(now > round_time + 10000) {
				ball_count++;
				double[] ball_x_vals_new = new double[ball_count];
				double[] ball_y_vals_new = new double[ball_count];
				double[] ball_x_vals_bounded_new = new double[ball_count];
				double[] ball_y_vals_bounded_new = new double[ball_count];
				for(int i = 0; i < ball_count - 1; i++) {
					ball_x_vals_new[i] = ball_x_vals[i];
					ball_y_vals_new[i] = ball_y_vals[i];
					ball_x_vals_bounded_new[i] = ball_x_vals_bounded[i];
					ball_y_vals_bounded_new[i] = ball_y_vals_bounded[i];
				}
				ball_x_vals_new[ball_count-1] = Math.random();
				ball_y_vals_new[ball_count-1] = Math.random();
				ball_x_vals_bounded_new[ball_count-1] = Math.random() * (upper_bound - lower_bound) + lower_bound;
				ball_y_vals_bounded_new[ball_count-1] = Math.random() * (upper_bound - lower_bound) + lower_bound;
				ball_x_vals = ball_x_vals_new;
				ball_y_vals = ball_y_vals_new;
				ball_x_vals_bounded = ball_x_vals_bounded_new;
				ball_y_vals_bounded = ball_y_vals_bounded_new;
				round_time = now;
			}
			StdDraw.setPenColor(Color.red);
			for(int i = 0; i < ball_count; i++) {
				StdDraw.filledCircle(ball_x_vals[i], ball_y_vals[i], radius);
			}
			
			StdDraw.setPenColor(Color.black);
			StdDraw.filledCircle(player_x, player_y, radius);
			StdDraw.text(0.5, 0.1, "Score: " + score + " High Score: " + high_score);
			
			StdDraw.show();
			StdDraw.pause(10);
			
		}
	}
}
