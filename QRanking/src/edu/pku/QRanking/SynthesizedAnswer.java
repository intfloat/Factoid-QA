package edu.pku.QRanking;

public class SynthesizedAnswer  implements Comparable<SynthesizedAnswer>{

	public String answer;
	public float score;
	
	@Override
	public int compareTo(SynthesizedAnswer another) {
		
	    if (this.score < another.score) {
	        return -1;
	    }
	    if (this.score > another.score) {
	        return 1;
	    }
	    return 0;
	}

}
