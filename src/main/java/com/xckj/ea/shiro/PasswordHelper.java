package com.xckj.ea.shiro;

import com.xckj.ea.dao.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelper {
    private static RandomNumberGenerator randomNumberGenerator=new SecureRandomNumberGenerator();
    public static final String ALGORITHM_NAME="md5";//基础散列算法
    public static final  int HASH_ITERATION=2;
    public void encrytPassword(User user)
    {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPwd=new SimpleHash(ALGORITHM_NAME,user.getPwd(),
                ByteSource.Util.bytes(user.getCrentialSalt()),HASH_ITERATION).toHex();
        user.setPwd(newPwd);
    }
}
