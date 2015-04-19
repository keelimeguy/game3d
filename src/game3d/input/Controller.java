package game3d.input;

public class Controller {
	public double x = 0;
	public double z = 0;
	public double y = 0;
	public double rotation = 0;
	public double xa = 0;
	public double za = 0;
	public double rotationa = 0;

	public static boolean turnLeft = false;
	public static boolean turnRight = false;

	public static boolean isWalking = false;
	public static boolean isCrouchWalk = false;
	public static boolean isRunning = false;

	public static boolean isJumping = false;
	public static int jumpTicks = 0;
	private static int MAX_JUMP_TICKS = 30;

	public void tick(boolean forward, boolean back, boolean left, boolean right, boolean jump, boolean crouch, boolean run) {
		double rotationSpeed = 0.02;
		double walkSpeed = 0.5;
		double jumpHeight = 0.5;
		double crouchHeight = 0.3;
		double xMove = 0;
		double zMove = 0;

		isWalking = false;
		isCrouchWalk = false;
		isRunning = false;

		if (forward) {
			zMove++;
			isWalking = true;
		}
		if (back) {
			zMove--;
			isWalking = true;
		}
		if (left) {
			xMove--;
			isWalking = true;
		}
		if (right) {
			xMove++;
			isWalking = true;
		}
		if (turnLeft) {
			rotationa -= rotationSpeed;
		}
		if (turnRight) {
			rotationa += rotationSpeed;
		}
		// Check if the player is still pressing on [SPACE]
		if (isJumping && !jump) {
			isJumping = false; // player released the key
			jumpTicks = 0;
		}
		if (jump && (!isJumping || jumpTicks <= MAX_JUMP_TICKS)) {
			y += jumpHeight;
			run = false;
			isJumping = true; // user has initiated a jump
			++jumpTicks;
		}
		if (crouch) {
			y -= crouchHeight;
			run = false;
			isCrouchWalk = true;
			walkSpeed = 0.2;
		}
		if (run) {
			walkSpeed = 1;
			isWalking = true;
			isRunning = true;
		}

		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;

		x += xa;
		y *= 0.9;
		z += za;
		xa *= 0.1;
		za *= 0.1;
		rotation += rotationa;
		rotationa *= 0.2;
	}
}
