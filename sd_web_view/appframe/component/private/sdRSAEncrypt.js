/**
 * RSA加密
 */
var sdRSAEncrypt = sdRSAEncrypt || {};

sdRSAEncrypt = (function(){
	
	var publicKey = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCp4VXMrnKCHEgV21Rhl15Dut/m8DypU4Db1GV7nj1W5QfzHK/joeUpXHpJpIzuqG9ajUOXxgwNhZcOlYsStpoQ18PdHOcY5N4jWCi5D0L8tAL+iAyPM62kbW4uBftVFrQvNTwRoy6chuRl44mjc4BrEIidHmh/m4GZ5zU1peUT5wIDAQAB';
	sdRSAEncrypt.encrypt = function (str) {
		
		var encrypt = new JSEncrypt();
        encrypt.setPublicKey(this.publicKey);
        var data = encrypt.encrypt(str);
        return data;
	}
	
	
	return{
		publicKey:publicKey,
        encrypt:sdRSAEncrypt.encrypt  
    }
})();