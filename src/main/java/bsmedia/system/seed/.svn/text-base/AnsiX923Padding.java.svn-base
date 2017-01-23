package bsmedia.system.seed;

/****************************************************
 * <PRE>
 * @Project Name	: KONGJU
 * @File Name		: AnsiX923Padding.java
 * @Comment			: ?�호?�에??블럭 ?�이즈�? 맞추�??�해 ?�용?�는 Padding??구체?�한 CryptoPadding???�체
 * @Author			: eunjung
 * @Creation Date	: 2012. 1. 13.?�후 4:10:30
 * Copyright ?�b&s media All Right Reserved
 * </PRE>
 ****************************************************/

public class AnsiX923Padding implements CryptoPadding {
	
	/** ?�딩 규칙 ?�름 */
	private String name = "ANSI-X.923-Padding";
	
	private final byte PADDING_VALUE = 0x00;
	
	/**
	 * ?�청??Block Size�?맞추�??�해 Padding??추�??�다.
	 * 
	 * @param source
	 *            byte[] ?�딩??추�???bytes
	 * @param blockSize
	 *            int block size
	 * @return byte[] ?�딩??추�? ??결과 bytes
	 */
	public byte[] addPadding(byte[] source, int blockSize) {
		int paddingCnt = source.length % blockSize;
		byte[] paddingResult = null;
		
		if(paddingCnt != 0) {
			paddingResult = new byte[source.length + (blockSize - paddingCnt)];
			
			System.arraycopy(source, 0, paddingResult, 0, source.length);
			
			// ?�딩?�야 ??�?�� - 1 (마�?막을 ?�외)까�? 0x00 값을 추�??�다.
			int addPaddingCnt = blockSize - paddingCnt;
			for(int i=0;i<addPaddingCnt;i++) {
				paddingResult[source.length + i] = PADDING_VALUE;
			}
			
			// 마�?�??�딩 값�? ?�딩 ??Count�?추�??�다.
			paddingResult[paddingResult.length - 1] = (byte)addPaddingCnt;			
		} else {
			paddingResult = source;
		}

		return paddingResult;
	}

	/**
	 * ?�청??Block Size�?맞추�??�해 추�? ??Padding???�거?�다.
	 * 
	 * @param source
	 *            byte[] ?�딩???�거??bytes
	 * @param blockSize
	 *            int block size
	 * @return byte[] ?�딩???�거 ??결과 bytes
	 */
	public byte[] removePadding(byte[] source, int blockSize) {
		byte[] paddingResult = null;
		boolean isPadding = false;
		
		// ?�딩 ??count�?찾는??
		int lastValue = source[source.length - 1];
		if(lastValue < (blockSize - 1)) {
			int zeroPaddingCount = lastValue - 1;
			
			for(int i=2;i<(zeroPaddingCount + 2);i++) {
				if(source[source.length - i] != PADDING_VALUE) {
					isPadding = false;
					break;
				}
			}
			
			isPadding = true;
		} else {
			// 마�?�?값이 block size 보다 ??경우 ?�딩 ?�것???�음.
			isPadding = false;
		}
		
		if(isPadding) {
			paddingResult = new byte[source.length - lastValue];
			System.arraycopy(source, 0, paddingResult, 0, paddingResult.length);
		} else {
			paddingResult = source;
		}		
		
		return paddingResult;
	}
	
	public String getName() {
		return name;
	}
	
}
