package jp.go.saga.controller.klb;

import java.text.Normalizer;
import java.text.Normalizer.Form;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		Test t = new Test();
		t.test();
	}

	 public void test() throws Exception {
			String str = "あいうえおー－㈱①";

			byte[] m932 = str.getBytes("MS932");
			System.out.println("MS932:" + new String(m932, "MS932"));

			byte[] sjis = str.getBytes("SJIS");
			System.out.println("SJIS :" + new String(sjis, "SJIS"));


	 	String zen = Normalizer.normalize("あいうえお１２３ＡＢＣ", Form.NFKC);
	 	System.out.println("ｱｲｳｴｵ : "+zen);

	 	StringBuilder sb = new StringBuilder(zen.length());
		for (int i = 0; i < zen.length(); i++) {
			char c = (char) (zen.charAt(i) + 'あ' - 'ア');
			sb.append(c);
		}
		String hira = sb.toString();
		System.out.println(zen+" : "+hira);
	 }
}
