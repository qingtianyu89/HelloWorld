package com.test.action.sphinx;

public class TestSphinx {
	
		public static void main(String[] args) {
			String sphinxHostr = "127.0.0.1";
			int sphinxPort = 9312;
			SphinxClient sc = new SphinxClient(sphinxHostr, sphinxPort);
			try {
				SphinxResult sr = sc.Query("ceshi");
				sc.SetLimits(1, 10);
				for(SphinxMatch sm:sr.matches){
					System.out.println(sm.docId);
				}
			} catch (SphinxException e) {
				e.printStackTrace();
			}
			
		}
}	
