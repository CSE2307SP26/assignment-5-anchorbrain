import java.awt.Color;

import edu.princeton.cs.introcs.StdDraw;

public class Game {

	final static double lower_bound = 0.005;
	final static double upper_bound = 0.01;
	final static double radius = 0.025;

	private int ball_count;
	private int score;
	private int high_score;
	private double[] ball_x_vals;
	private double[] ball_y_vals;
	private double[] ball_x_vals_bounded;
	private double[] ball_y_vals_bounded;
	private long score_timer;
	private long round_time;
	private Player player;

	public Game() {
		this.player = new Player();
		this.ball_count = 3;
		this.score = 0;
		this.high_score = 0;
		this.ball_x_vals = new double[ball_count];
		this.ball_y_vals = new double[ball_count];
		this.ball_x_vals_bounded = new double[ball_count];
		this.ball_y_vals_bounded = new double[ball_count];
		for(int i = 0; i < ball_count; i++) {
			ball_x_vals[i] = Math.random();
			ball_y_vals[i] = Math.random();
			ball_x_vals_bounded[i] = Math.random() * (upper_bound - lower_bound) + lower_bound;
			ball_y_vals_bounded[i] = Math.random() * (upper_bound - lower_bound) + lower_bound;
		}
		this.score_timer = System.currentTimeMillis();
		this.round_time = System.currentTimeMillis();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.run();
	}

	private void run() {
		StdDraw.enableDoubleBuffering();
		while (true) {
			run_game_tick();
		}
	}

	private void run_game_tick() {
		StdDraw.clear();

		handle_collision();
		player.handle_input();
		player.handle_bounds();

		check_score_timer();
		check_difficulty_timer();

		draw_enemies();
		player.draw();
		draw_score();
		draw_frame();
	}

	private void handle_collision() {
		handle_enemy_collision();
		handle_player_collision();
	}

	private void handle_enemy_collision() {
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
		}
	}

	private void handle_player_collision() {
		boolean player_collision = false;
		for (int i = 0; i < ball_count; i++) {
			double distance_to_player = Math.sqrt(Math.pow(ball_x_vals[i] - player.get_x(), 2) + Math.pow(ball_y_vals[i] - player.get_y(), 2));
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
				player.set_x(0.5);
				player.set_y(0.5);
			}
		}
	}

	private void check_score_timer() {
		long now = System.currentTimeMillis();
		if(now > score_timer + 1000) {
			score++;
			if(score > high_score) {
				high_score = score;
			}
			score_timer = now;
		}
	}

	private void check_difficulty_timer() {
		if(score_timer > round_time + 10000) {
			add_enemy();
		}
	}

	private void add_enemy() {
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
		round_time = score_timer;
	}

	private void draw_enemies() {
		StdDraw.setPenColor(Color.red);
		for(int i = 0; i < ball_count; i++) {
			StdDraw.filledCircle(ball_x_vals[i], ball_y_vals[i], radius);
		}
	}

	private void draw_score() {
		StdDraw.setPenColor(Color.black);
		StdDraw.text(0.5, 0.1, "Score: " + score + " High Score: " + high_score);
	}

	private void draw_frame() {
		StdDraw.show();
		StdDraw.pause(10);
	}
}
