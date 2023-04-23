package world;

public class Camera {
	
	public static int x;
	public static int y;
	
	// conta para auxiliar a tirar as bordas pretas e so mostrar o que tem no mapa de fato
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
