package world;

public class Camera {
	
	public static int x;
	public static int y;
	
	// conta para prender a camera no jogador
	public static int clamp(int Atual, int Min,int Max) {
		if (Atual < Min) {
			Atual = Min;
		}
		if (Atual > Max) {
			Atual = Max;
		}
		return Atual;
	}

}
