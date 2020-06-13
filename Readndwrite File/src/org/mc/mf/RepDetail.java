package org.mc.mf;


public class RepDetail {
	
	private String initial = "RDTL";
	private String firstCol;
	private String secondCol;
	private String thirdCol;
	private String fourthCol;
	
	private final String nineSpace = "         ";
	private final String twelveSpace = "            ";
	
	public String getFirstCol() {
		return firstCol;
	}
	public void setFirstCol(String firstCol) {
		this.firstCol = firstCol;
	}
	public String getSecondCol() {
		return secondCol;
	}
	public void setSecondCol(String secondCol) {
		this.secondCol = secondCol;
	}
	public String getThirdCol() {
		return thirdCol;
	}
	public void setThirdCol(String thirdCol) {
		this.thirdCol = thirdCol;
	}
	public String getFourthCol() {
		return fourthCol;
	}
	public void setFourthCol(String fourthCol) {
		this.fourthCol = fourthCol;
	}
	public String getInitial() {
		return initial;
	}
	
	@Override
	public String toString() {
		StringBuilder finalLine = new StringBuilder();
		int requiredSpace = 120 - (initial + nineSpace + firstCol + " " + secondCol).length();
		StringBuilder addSpace = new StringBuilder();
		for(int i=0; i<requiredSpace; i++) {
			addSpace.append(" ");
		}
		return finalLine.append(initial).append(nineSpace).append(firstCol).append(" ").append(secondCol).append(addSpace).append(thirdCol).append(twelveSpace).append(twelveSpace).append(fourthCol).toString();
		//return initial + fourSpace + firstCol + " " + secondCol + "\t\t\t\t\t"+ thirdCol + "\t\t\t" + fourthCol;
	}
	
	
}
