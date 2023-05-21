package world;

public class Camera {
	
	public static int x;
	public static int y;
	
	// Método possuinte de uma condicional que auxilia a tirar as bordas pretas e só mostrar o que tem no mapa de fato.
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
