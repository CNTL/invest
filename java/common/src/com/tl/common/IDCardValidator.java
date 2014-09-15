package com.tl.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * <p>
 * ��˵��:���֤�Ϸ���У��
 * </p>
 * <p>
 * --15λ���֤���룺��7��8λΪ�������(��λ��)����9��10λΪ�����·ݣ���11��12λ����������ڣ���15λ�����Ա�����Ϊ�У�ż��ΪŮ��
 * --18λ���֤����
 * ����7��8��9��10λΪ�������(��λ��)����11����12λΪ�����·ݣ���13��14λ����������ڣ���17λ�����Ա�����Ϊ�У�ż��ΪŮ��
 * </p>
 * <p>
 * �ж�18λ���֤�ĺϷ���
 * </p>
 * ���ݡ��л����񹲺͹����ұ�׼GB11643-1999�����йع�����ݺ���Ĺ涨��������ݺ�������������룬��ʮ��λ���ֱ������һλ����У������ɡ�
 * ����˳�������������Ϊ����λ���ֵ�ַ�룬��λ���ֳ��������룬��λ����˳�����һλ����У���롣
 * <p>
 * ˳����: ��ʾ��ͬһ��ַ������ʶ������Χ�ڣ���ͬ�ꡢͬ�¡�ͬ �ճ������˱ඨ��˳��ţ�˳�����������������ԣ�ż������ ��Ů�ԡ�
 * </p>
 * <p>
 * 1.ǰ1��2λ���ֱ�ʾ������ʡ�ݵĴ��룻 2.��3��4λ���ֱ�ʾ�����ڳ��еĴ��룻 3.��5��6λ���ֱ�ʾ���������صĴ��룻
 * 4.��7~14λ���ֱ�ʾ�������ꡢ�¡��գ� 5.��15��16λ���ֱ�ʾ�����ڵص��ɳ����Ĵ��룻 6.��17λ���ֱ�ʾ�Ա�������ʾ���ԣ�ż����ʾŮ�ԣ�
 * 7.��18λ������У���룺Ҳ�е�˵�Ǹ�����Ϣ�룬һ��������������������������������֤����ȷ�ԡ�У���������0~9�����֣���ʱҲ��x��ʾ��
 * </p>
 * <p>
 * ��ʮ��λ����(У����)�ļ��㷽��Ϊ�� 1.��ǰ������֤����17λ���ֱ���Բ�ͬ��ϵ�����ӵ�һλ����ʮ��λ��ϵ���ֱ�Ϊ��7 9 10 5 8 4 2 1
 * 6 3 7 9 10 5 8 4 2
 * </p>
 * <p>
 * 2.����17λ���ֺ�ϵ����˵Ľ����ӡ�
 * </p>
 * <p>
 * 3.�üӳ����ͳ���11���������Ƕ��٣�
 * </p>
 * 4.����ֻ������0 1 2 3 4 5 6 7 8 9 10��11�����֡���ֱ��Ӧ�����һλ���֤�ĺ���Ϊ1 0 X 9 8 7 6 5 4 3 2��
 * <p>
 * 5.ͨ�������֪���������2���ͻ������֤�ĵ�18λ�����ϳ����������ֵĢ������������10�����֤�����һλ�������2��
 * </p>
 */
@SuppressWarnings({ "unchecked", "unused", "all" })
public class IDCardValidator {
	/**
	 * ʡ��ֱϽ�д���� { 11:"����",12:"���",13:"�ӱ�",14:"ɽ��",15:"���ɹ�",
	 * 21:"����",22:"����",23:"������",31:"�Ϻ�",32:"����",
	 * 33:"�㽭",34:"����",35:"����",36:"����",37:"ɽ��",41:"����",
	 * 42:"����",43:"����",44:"�㶫",45:"����",46:"����",50:"����",
	 * 51:"�Ĵ�",52:"����",53:"����",54:"����",61:"����",62:"����",
	 * 63:"�ຣ",64:"����",65:"�½�",71:"̨��",81:"���",82:"����",91:"����"}
	 */
	private final static String codeAndCity[][] = { { "11", "����" },
			{ "12", "���" }, { "13", "�ӱ�" }, { "14", "ɽ��" }, { "15", "���ɹ�" },
			{ "21", "����" }, { "22", "����" }, { "23", "������" }, { "31", "�Ϻ�" },
			{ "32", "����" }, { "33", "�㽭" }, { "34", "����" }, { "35", "����" },
			{ "36", "����" }, { "37", "ɽ��" }, { "41", "����" }, { "42", "����" },
			{ "43", "����" }, { "44", "�㶫" }, { "45", "����" }, { "46", "����" },
			{ "50", "����" }, { "51", "�Ĵ�" }, { "52", "����" }, { "53", "����" },
			{ "54", "����" }, { "61", "����" }, { "62", "����" }, { "63", "�ຣ" },
			{ "64", "����" }, { "65", "�½�" }, { "71", "̨��" }, { "81", "���" },
			{ "82", "����" }, { "91", "����" } };

	private final static String cityCode[] = { "11", "12", "13", "14", "15",
			"21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41",
			"42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61",
			"62", "63", "64", "65", "71", "81", "82", "91" };

	// ÿλ��Ȩ����

	private final static int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9,
			10, 5, 8, 4, 2 };
	/**
	 * ��֤���֤�����Ƿ�Ϸ�
	 * 
	 * @param idcard
	 *            ���֤���룬֧��15λ��18λ
	 * @return true:�Ϸ� false:���Ϸ�
	 */
	public static boolean isValidatedIdcard(String idcard) {
		if (idcard.length() == 15) {
			idcard = convertIdcarBy15bit(idcard);
		}
		return isValidate18Idcard(idcard);
	}

	private static boolean isValidate18Idcard(String idcard) {
		// ��18λΪ��
		if (idcard.length() != 18) {
			return false;
		}
		// ��ȡǰ17λ
		String idcard17 = idcard.substring(0, 17);
		// ��ȡ��18λ
		String idcard18Code = idcard.substring(17, 18);
		char c[] = null;
		String checkCode = "";
		// �Ƿ�Ϊ����
		if (isDigital(idcard17)) {
			c = idcard17.toCharArray();
		} else {
			return false;
		}

		if (null != c) {

			int bit[] = converCharToInt(c);

			int sum17 = 0;

			sum17 = getPowerSum(bit);

			// ����ֵ��11ȡģ�õ���������У�����ж�
			checkCode = getCheckCodeBySum(sum17);
			if (null == checkCode) {
				return false;
			}
			// �����֤�ĵ�18λ���������У�����ƥ�䣬����Ⱦ�Ϊ��
			if (!idcard18Code.equalsIgnoreCase(checkCode)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ��֤15λ���֤�ĺϷ���,�÷�����֤��׼ȷ������ǽ�15תΪ18λ�����жϣ����������ṩ��
	 * 
	 * @param idcard
	 * @return
	 */
	private boolean isValidate15Idcard(String idcard) {
		// ��15λΪ��
		if (idcard.length() != 15) {
			return false;
		}

		// �Ƿ�ȫ��Ϊ����
		if (isDigital(idcard)) {
			String provinceid = idcard.substring(0, 2);
			String birthday = idcard.substring(6, 12);
			int year = Integer.parseInt(idcard.substring(6, 8));
			int month = Integer.parseInt(idcard.substring(8, 10));
			int day = Integer.parseInt(idcard.substring(10, 12));

			// �ж��Ƿ�Ϊ�Ϸ���ʡ��
			boolean flag = false;
			for (String id : cityCode) {
				if (id.equals(provinceid)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				return false;
			}
			// �ж��Ƿ�Ϊ�Ϸ���ʡ��
			Date birthdate = null;
			try {
				birthdate = new SimpleDateFormat("yyMMdd",Locale.getDefault()).parse(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (birthdate == null || new Date().before(birthdate)) {
				return false;
			}

			// �ж��Ƿ�Ϊ�Ϸ���ʡ��
			GregorianCalendar curDay = new GregorianCalendar();
			int curYear = curDay.get(Calendar.YEAR);
			int year2bit = Integer.parseInt(String.valueOf(curYear)
					.substring(2));

			// �жϸ���ݵ���λ��ʾ����С��50�ĺʹ��ڵ�ǰ��ݵģ�Ϊ��
			if ((year < 50 && year > year2bit)) {
				return false;
			}

			// �ж��Ƿ�Ϊ�Ϸ����·�
			if (month < 1 || month > 12) {
				return false;
			}

			// �ж��Ƿ�Ϊ�Ϸ�������
			boolean mflag = false;
			curDay.setTime(birthdate); // �ж��Ƿ�Ϊ�Ϸ�������

			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				mflag = (day >= 1 && day <= 31);
				break;
			case 2:// �ж��Ƿ�Ϊ�Ϸ�������
				if (curDay.isLeapYear(curDay.get(Calendar.YEAR))) {
					mflag = (day >= 1 && day <= 29);
				} else {
					mflag = (day >= 1 && day <= 28);
				}
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				mflag = (day >= 1 && day <= 30);
				break;
			}
			if (!mflag) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * ��15λ�����֤ת��18λ���֤
	 * 
	 * @param idcard
	 *            ��Ҫת����15λ���֤��
	 * @return ת�����18λ���֤���룬���Ϊnull����ʾת��ʧ��
	 */
	public static String convertIdcarBy15bit(String idcard) {
		String idcard17 = null;
		// ��15λ���֤
		if (idcard.length() != 15) {
			return null;
		}

		if (isDigital(idcard)) {
			// ��ȡ����������
			String birthday = idcard.substring(6, 12);
			Date birthdate = null;
			try {
				birthdate = new SimpleDateFormat("yyMMdd",Locale.getDefault()).parse(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
			Calendar cday = Calendar.getInstance();
			cday.setTime(birthdate);
			String year = String.valueOf(cday.get(Calendar.YEAR));

			idcard17 = idcard.substring(0, 6) + year + idcard.substring(8);

			char c[] = idcard17.toCharArray();
			String checkCode = "";

			if (null != c) {

				// ���ַ�����תΪ��������
				int bit[] = converCharToInt(c);
				int sum17 = 0;
				sum17 = getPowerSum(bit);

				// ��ȡ��ֵ��11ȡģ�õ���������У����
				checkCode = getCheckCodeBySum(sum17);
				// ��ȡ��ֵ��11ȡģ�õ���������У����
				if (null == checkCode) {
					return null;
				}

				// ��ȡ��ֵ��11ȡģ�õ���������У����
				idcard17 += checkCode;
			}
		} else { // ���֤��������
			return null;
		}
		return idcard17;
	}

	/**
	 * 15λ��18λ���֤����Ļ������ֺ�λ����У
	 * 
	 * @param idcard
	 * @return
	 */
	private boolean isIdcard(String idcard) {
		return idcard == null || "".equals(idcard) ? false : Pattern.matches(
				"(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)", idcard);
	}

	/**
	 * ������֤
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isDigital(String str) {
		return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
	}

	/**
	 * ������֤
	 * 
	 * @param str
	 * @return
	 */
	private static int getPowerSum(int[] bit) {

		int sum = 0;

		if (power.length != bit.length) {
			return sum;
		}

		for (int i = 0; i < bit.length; i++) {
			for (int j = 0; j < power.length; j++) {
				if (i == j) {
					sum = sum + bit[i] * power[j];
				}
			}
		}
		return sum;
	}

	private final static String sum_checkCode[] = {"1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2", };

	/**
	 * ����ֵ��11ȡģ�õ���������У�����ж�
	 * 
	 * @param checkCode
	 * @param sum17
	 * @return У��λ
	 */
	private static String getCheckCodeBySum(int sum17) {
		return sum_checkCode[sum17 % 11];
	}

	/**
	 * ����ֵ��11ȡģ�õ���������У�����ж�
	 * 
	 * @param checkCode
	 * @param sum17
	 * @return У��λ
	 */
	private static int[] converCharToInt(char[] c) throws NumberFormatException {
		int[] a = new int[c.length];
		int k = 0;
		for (char temp : c) {
			a[k++] = Integer.parseInt(String.valueOf(temp));
		}
		return a;
	}

	/**
	 * ������������
	 * 
	 * @param date
	 *            ָ������
	 * @param year
	 *            ������
	 */
	private static Date addYear(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());

		int currenYear = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, year);

		return new Date(calendar.getTimeInMillis());
	}

	/**
	 * �ж���������֤�����Ƿ��ǳ�����
	 * @param idcard
	 * @return
	 */
	public static boolean isAdult(String idcard) {
		if (idcard.length() == 15) {
			idcard = convertIdcarBy15bit(idcard);
		}
		// ��ȡ��������
		String birthday = idcard.substring(6, 14);
		Date birthdate = null;
		try {
			birthdate = new SimpleDateFormat("yyyyMMdd",Locale.getDefault()).parse(birthday);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date curr = new Date();
		Date dateAdd18 = addYear(birthdate, 18);
		if (dateAdd18.getTime() < curr.getTime()) {
			return true;
		} else {
			return false;
		}
	}
}
