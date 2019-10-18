package com.gateway.tool;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class BytesUtils {
	
	public static byte[] longToBytes(long number) {
		long temp = number;
		byte[] result = new byte[8];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Long(temp & 0xff).byteValue();
			temp = temp >> 8;
		}
		return result;
	}
	
	public static long bytesToLong(byte[] bytes) {
		return ((((long) bytes[0] & 0xff) << 56)
				| (((long) bytes[1] & 0xff) << 48)
				| (((long) bytes[2] & 0xff) << 40)
				| (((long) bytes[3] & 0xff) << 32)
				| (((long) bytes[4] & 0xff) << 24)
				| (((long) bytes[5] & 0xff) << 16)
				| (((long) bytes[6] & 0xff) << 8) 
				| (((long) bytes[7] & 0xff) << 0));
	}

	public static byte[] intToBytes(int number) {
		int temp = number;
		byte[] result = new byte[4];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Integer(temp & 0xff).byteValue();
			temp = temp >> 8;
		}
		return result;
	}

	public static byte[] shortToBytes(int number) {
		int temp = number;
		byte[] result = new byte[2];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Integer(temp & 0xff).byteValue();
			temp = temp >> 8;
		}
		return result;
	}

	public static byte[] byteToBytes(byte number) {
		byte[] result = new byte[1];
		result[0] = number;
		return result;
	}

	public static byte[] stringToBytes(String str) {
		try {
			return str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] booleanToBytes(boolean param) {
		return byteToBytes(param == true ? (byte)1 : (byte)0);
	}

	//转义序列还原（0x2A 0x23）
	public static byte[] unreverseMeaning(byte[] buf) {
		List<Byte> result = new ArrayList<Byte>();
		int num = buf.length;
		for(int i = 0; i < num; ++i) {
			if(buf[i] == (byte)0x7b && buf[i+1] == (byte)0x02) {
				result.add((byte)0x2a);
				i++;
				continue;
			}
			if(buf[i] == (byte)0x7b && buf[i+1] == (byte)0x01) {
				result.add((byte)0x7b);
				i++;
				continue;
			}
			if(buf[i] == (byte)0x7d && buf[i+1] == (byte)0x02) {
				result.add((byte)0x23);
				i++;
				continue;
			}
			if(buf[i] == (byte)0x7d && buf[i+1] == (byte)0x01) {
				result.add((byte)0x7d);
				i++;
				continue;
			}
			result.add((byte)buf[i]);
		}
		byte[] rlt_rtn = new byte[result.size()];
		for(int k = 0; k < result.size(); k++) {
			rlt_rtn[k] = result.get(k);
		}
		System.out.println();
		return rlt_rtn;
	}

	//转义序列
	public static byte[] reverseMeaning(byte[] buf) {
		ByteArrayOutputStream resultStreamFinal = new ByteArrayOutputStream();
		DataOutputStream resultFinal = new DataOutputStream(resultStreamFinal);
		try {
			resultFinal.writeByte(buf[0]);
			for (int i = 1; i < buf.length - 1; ++i) {
				if (buf[i] == (byte) 0x2A) {
					resultFinal.writeByte((byte) 0x7B);
					resultFinal.writeByte((byte) 0x02);
				}else if (buf[i] == (byte) 0x7B) {
					resultFinal.writeByte((byte) 0x7B);
					resultFinal.writeByte((byte) 0x01);
				}else if (buf[i] == (byte) 0x23) {
					resultFinal.writeByte((byte) 0x7D);
					resultFinal.writeByte((byte) 0x02);
				}else if (buf[i] == (byte) 0x7D) {
					resultFinal.writeByte((byte) 0x7D);
					resultFinal.writeByte((byte) 0x01);
				} else {
					resultFinal.writeByte((byte) buf[i]);
				}
			}
			resultFinal.writeByte(buf[buf.length - 1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultStreamFinal.toByteArray();
	}

	/**
	 * BCD字节数组===>String
	 *
	 * @param bytes
	 * @return 十进制字符串
	 */
	public static String bcd2String(byte[] bytes) {
		StringBuilder temp = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			// 高四位
			temp.append((bytes[i] & 0xf0) >>> 4);
			// 低四位
			temp.append(bytes[i] & 0x0f);
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();
	}

	/**
	 * 字符串==>BCD字节数组
	 *
	 * @param str
	 * @return BCD字节数组
	 */
	public byte[] string2Bcd(String str) {
		// 奇数,前补零
		if ((str.length() & 0x1) == 1) {
			str = "0" + str;
		}

		byte ret[] = new byte[str.length() / 2];
		byte bs[] = str.getBytes();
		for (int i = 0; i < ret.length; i++) {

			byte high = ascII2Bcd(bs[2 * i]);
			byte low = ascII2Bcd(bs[2 * i + 1]);

			// TODO 只遮罩BCD低四位?
			ret[i] = (byte) ((high << 4) | low);
		}
		return ret;
	}

	private byte ascII2Bcd(byte asc) {
		if ((asc >= '0') && (asc <= '9'))
			return (byte) (asc - '0');
		else if ((asc >= 'A') && (asc <= 'F'))
			return (byte) (asc - 'A' + 10);
		else if ((asc >= 'a') && (asc <= 'f'))
			return (byte) (asc - 'a' + 10);
		else
			return (byte) (asc - 48);
	}
}
