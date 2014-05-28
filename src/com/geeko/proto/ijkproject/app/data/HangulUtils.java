package com.geeko.proto.ijkproject.app.data;

public class HangulUtils {

	/*
	 * 
	 * @param decimal
	 * 
	 * @return
	 */
	private static String toHexString(int decimal) {
		Long intDec = Long.valueOf(decimal);
		return Long.toHexString(intDec);
	}

	public static final int HANGUL_BEGIN_UNICODE = 44032; // ��
	public static final int HANGUL_END_UNICODE = 55203; // �R
	public static final int HANGUL_BASE_UNIT = 588;

	public static final int[] INITIAL_SOUND_UNICODE = { 12593, 12594, 12596,
			12599, 12600, 12601, 12609, 12610, 12611, 12613, 12614, 12615,
			12616, 12617, 12618, 12619, 12620, 12621, 12622 };

	public static final char[] INITIAL_SOUND = { '��', '��', '��', '��', '��', '��',
			'��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��' };

	/**
	 * ���ڸ� �����ڵ�(10����)�� ��ȯ �� ��ȯ �Ѵ�.
	 * 
	 * @param ch
	 * @return
	 */
	public static int convertCharToUnicode(char ch) {
		return (int) ch;
	}

	/**
	 * ���ڿ��� �����ڵ�(10����)�� ��ȯ �� ��ȯ �Ѵ�.
	 * 
	 * @param str
	 * @return
	 */
	public static int[] convertStringToUnicode(String str) {

		int[] unicodeList = null;

		if (str != null) {
			unicodeList = new int[str.length()];
			for (int i = 0; i < str.length(); i++) {
				unicodeList[i] = convertCharToUnicode(str.charAt(i));
			}
		}

		return unicodeList;
	}

	/**
	 * �����ڵ�(16����)�� ���ڷ� ��ȯ �� ��ȯ �Ѵ�.
	 * 
	 * @param hexUnicode
	 * @return
	 */
	public static char convertUnicodeToChar(String hexUnicode) {
		return (char) Integer.parseInt(hexUnicode, 16);
	}

	/**
	 * �����ڵ�(10����)�� ���ڷ� ��ȯ �� ��ȯ �Ѵ�.
	 * 
	 * @param unicode
	 * @return
	 */
	public static char convertUnicodeToChar(int unicode) {
		return convertUnicodeToChar(toHexString(unicode));
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String getHangulInitialSound(String value) {

		StringBuffer result = new StringBuffer();

		int[] unicodeList = convertStringToUnicode(value);
		for (int unicode : unicodeList) {

			if (HANGUL_BEGIN_UNICODE <= unicode
					&& unicode <= HANGUL_END_UNICODE) {
				int tmp = (unicode - HANGUL_BEGIN_UNICODE);
				int index = tmp / HANGUL_BASE_UNIT;
				result.append(INITIAL_SOUND[index]);
			} else {
				result.append(convertUnicodeToChar(unicode));

			}
		}

		return result.toString();
	}

	public static boolean[] getIsChoSungList(String name) {
		if (name == null) {
			return null;
		}

		boolean[] choList = new boolean[name.length()];

		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);

			boolean isCho = false;
			for (char cho : INITIAL_SOUND) {
				if (c == cho) {
					isCho = true;
					break;
				}
			}

			choList[i] = isCho;

		}

		return choList;
	}

	public static String getHangulInitialSound(String value,
			String searchKeyword) {
		return getHangulInitialSound(value, getIsChoSungList(searchKeyword));
	}

	public static String getHangulInitialSound(String value, boolean[] isChoList) {

		StringBuffer result = new StringBuffer();

		int[] unicodeList = convertStringToUnicode(value);
		for (int idx = 0; idx < unicodeList.length; idx++) {
			int unicode = unicodeList[idx];

			if (isChoList != null && idx <= (isChoList.length - 1)) {
				if (isChoList[idx]) {
					if (HANGUL_BEGIN_UNICODE <= unicode
							&& unicode <= HANGUL_END_UNICODE) {
						int tmp = (unicode - HANGUL_BEGIN_UNICODE);
						int index = tmp / HANGUL_BASE_UNIT;
						result.append(INITIAL_SOUND[index]);
					} else {
						result.append(convertUnicodeToChar(unicode));
					}
				} else {
					result.append(convertUnicodeToChar(unicode));
				}
			} else {
				result.append(convertUnicodeToChar(unicode));
			}
		}

		return result.toString();
	}

	public static void main(String[] args) {

		for (char ch : HangulUtils.INITIAL_SOUND) {
			System.out.print(HangulUtils.convertCharToUnicode(ch) + ",");
		}
	}
}
