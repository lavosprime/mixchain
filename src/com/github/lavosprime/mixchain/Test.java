package com.github.lavosprime.mixchain;

import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		String[] names = new String[] {
				"Constant Motion.wav",
				"F.C.P.R.E.M.I.X..wav"
		};
		for (String s : names)
			System.out.println(Arrays.toString(FileCrawler.makeParts(s)));
	}

}