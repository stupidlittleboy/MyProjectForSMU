
package com.shmtu.myprojectforsmu.utils;

import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
/**
 * IDEA对称加密算法，java6不支持这个算法的实现，bouncycastle支持IDEA对称加密算法
 * 这是一款对称分组密码。是目前比较常用的电子邮件加密算法之一
 * 我们可以参照这个算法的实现来完成其他算法的实现：Rijndael,Serpent,Twofish等
 * @author kongqz
 * */
public class IDEACoder {
	/**
	 * 密钥算法
	 * */
	public static final String KEY_ALGORITHM="IDEA";

	/**
	 * 加密/解密算法/工作模式/填充方式
	 * */
	public static final String CIPHER_ALGORITHM="IDEA/ECB/ISO10126Padding";

	public static byte[] key = null;

	/**
	 * 
	 * 生成密钥，只有bouncycastle支持
	 * @return byte[] 二进制密钥
	 * */
	public static byte[] initkey() throws Exception{
		//加入bouncyCastle支持
		Security.addProvider(new BouncyCastleProvider());

		//实例化密钥生成器
		KeyGenerator kg=KeyGenerator.getInstance(KEY_ALGORITHM);
		//初始化密钥生成器，IDEA要求密钥长度为128位
		kg.init(128);
		//生成密钥
		SecretKey secretKey=kg.generateKey();
		//获取二进制密钥编码形式
		return secretKey.getEncoded();
	}
	/**
	 * 转换密钥
	 * @param key 二进制密钥
	 * @return Key 密钥
	 * */
	public static Key toKey(byte[] key) throws Exception{
		//实例化DES密钥
		//生成密钥
		SecretKey secretKey=new SecretKeySpec(key,KEY_ALGORITHM);
		return secretKey;
	}

	/**
	 * 加密数据
	 * @param data 待加密数据
	 * @param key 密钥
	 * @return byte[] 加密后的数据
	 * */
	public static byte[] encrypt(byte[] data,byte[] key) throws Exception{
		//加入bouncyCastle支持
		Security.addProvider(new BouncyCastleProvider());
		//还原密钥
		Key k=toKey(key);
		//实例化
		Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
		//初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		//执行操作
		return cipher.doFinal(data);
	}
	/**
	 * 解密数据
	 * @param data 待解密数据
	 * @param key 密钥
	 * @return byte[] 解密后的数据
	 * */
	public static byte[] decrypt(byte[] data,byte[] key) throws Exception{
		//加入bouncyCastle支持
		Security.addProvider(new BouncyCastleProvider());
		//还原密钥
		Key k =toKey(key);
		Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
		//初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);
		//执行操作
		return cipher.doFinal(data);
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static String encryptCode(String str) {
		//初始化密钥
		try {
			key = IDEACoder.initkey();
//			System.out.println("密钥："+Base64.encodeBase64String(key));
			//加密数据
			byte[] data=IDEACoder.encrypt(str.getBytes(), key);
			return Base64.encodeBase64String(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decryptCode(byte[] data) {
		try {
			key = IDEACoder.initkey();
			//解密数据
			data=IDEACoder.decrypt(data, key);
			return new String(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
//控制台输出结果：
//原文：IDEA
//密钥：TIM+ksMQUeUe6LHHBGntag==
//加密后：rRHc34j86i4=
//解密后：IDEA
