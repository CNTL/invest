package com.tl.security;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;

 

import com.tl.common.RSAUtils;
import com.tl.kernel.web.BaseController;

/**
 * 密码传输加密--取公钥
 * @author wang.yq
 * 2014-8-29
 */
public class PubKeyController extends BaseController
{
	@SuppressWarnings("rawtypes")
	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response,
			Map model) throws Exception
	{
		RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
		String modulus = new String(Hex.encodeHex(publicKey.getModulus().toByteArray()));
		String exponent = new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray()));
		
		StringBuilder out = new StringBuilder();
		out.append("<rsapubkey>");
		out.append("<modulus>").append(modulus).append("</modulus>");
		out.append("<exponent>").append(exponent).append("</exponent>");
		out.append("</rsapubkey>");
		
		output(out.toString(), response, true);
	}
}
