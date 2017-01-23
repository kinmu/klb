package bsmedia.system.seed;


/****************************************************
 * <PRE>
 * @Project Name	: KONGJU
 * @File Name		: CryptoPadding.java
 * @Comment			: ?�호?�에??블럭 ?�이즈�? 맞추�??�해 ?�용?�는 Padding??추상????Interface
 * @Author			: eunjung
 * @Creation Date	: 2012. 1. 13.?�후 4:17:13
 * Copyright ?�b&s media All Right Reserved
 * </PRE>
 ****************************************************/
public interface CryptoPadding {

	/**
	 * ?�청??Block Size�?맞추�??�해 Padding??추�??�다.
	 * 
	 * @param source
	 *            byte[] ?�딩??추�???bytes
	 * @param blockSize
	 *            int block size
	 * @return byte[] ?�딩??추�? ??결과 bytes
	 */
	public byte[] addPadding(byte[] source, int blockSize);

	/**
	 * ?�청??Block Size�?맞추�??�해 추�? ??Padding???�거?�다.
	 * 
	 * @param source
	 *            byte[] ?�딩???�거??bytes
	 * @param blockSize
	 *            int block size
	 * @return byte[] ?�딩???�거 ??결과 bytes
	 */
	public byte[] removePadding(byte[] source, int blockSize);

}
