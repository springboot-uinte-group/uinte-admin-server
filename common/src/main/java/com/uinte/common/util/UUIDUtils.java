package com.uinte.common.util;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.lang3.StringUtils;

public class UUIDUtils {
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };

	private static final int LEN = 8;
	private static final int NUMLEN = 10000;

	public static String shortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < LEN; i++) {
			String str = uuid.substring(i * 4, i * 4 + random(0, 4));
			if (StringUtils.isEmpty(str)) {
				shortBuffer.append(random(1, NUMLEN));
			} else {
				int x = Integer.parseInt(str, 16);
				String _st = chars[x % 0x3E];
				shortBuffer.append(_st.toUpperCase());
			}
		}
		return shortBuffer.toString();

	}

	public static int random(int star, int limit) {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		int ran = random.nextInt(star, limit);
		return ran;
	}
	
	public static Boolean trueOrFalse() {
		Long g = Math.round(Math.random());
		return g.equals(1L);
	}

	public static void main(String[] args) {
		Long count = 1000L;
		for (int i = 0; i < count; i++) {
			System.out.println(trueOrFalse());
		}
	}

	public static String uuidPK() {
		return UUID.randomUUID().toString();
	}

	public static String getCode(String key) {
		return shortUuid();
	}

}
