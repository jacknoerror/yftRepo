package com.qfc.yft.ui.shop;

import java.util.Hashtable;


import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.qfc.yft.R;
import com.qfc.yft.utils.Base64;
import com.qfc.yft.utils.JackUtils;

public class ShActivityAdd extends ShopTabAbsActivity{
	private final String TAG = this.getClass().getSimpleName();
	
	TextView tv_name,tv_webpage,tv_phone,tv_fax,tv_cell,tv_remark;
	ImageView qr_image;
	
	String qr_text="jack is a genius";//
	
	@Override
	public int getLayoutResId() {
		View viewToLoad = LayoutInflater.from(this.getParent()).inflate(R.layout.layout_sh_add,null);//this solves the incompatibility between dialog and tabactivity's child tab --taotao 1112
		setContentView(viewToLoad);
		return 0;
	}
	
	@Override
	public void initUI(){
		tv_name = (TextView)findViewById(R.id.tv_add11_name);
		tv_webpage = (TextView)findViewById(R.id.tv_add21_web);
		tv_phone = (TextView)findViewById(R.id.tv_add22_phone);
		tv_fax = (TextView)findViewById(R.id.tv_add23_fax);
		tv_cell = (TextView)findViewById(R.id.tv_add24_mobile);
		tv_remark = (TextView)findViewById(R.id.tv_add3_remark);
		
		qr_image = (ImageView)findViewById(R.id.img_qr_add);
		
	}
	
	@Override
	public void updateUI() {
			//	compContacter compUrl compPhone compFax compCorpMobile compRemarks
//		tv_name .setText(shHelper.dfCompany.getCompContacter());
		tv_name .setText(shHelper.dfCompany.getCompCorp());//1209
		String url = "http://"+shHelper.dfCompany.getCompUrl();
		tv_webpage .setText(url);
		tv_phone .setText(shHelper.dfCompany.getCompPhone());
		tv_fax .setText(shHelper.dfCompany.getCompFax());
		tv_cell .setText(shHelper.dfCompany.getCompCorpMobile());
		tv_remark .setText(shHelper.dfCompany.getCompRemarks());
		
		String devi = "-|-";
		try {
			qr_text = "http://"+shHelper.dfCompany.getCompUrl()+"?data="
					+shHelper.dfUser.getShopId()+devi
//					+new APIDesUtils().encrypt(shHelper.dfCompany.getCompName(), YftValues.DES_KEY)
					+Base64.encode(shHelper.dfCompany.getCompName().getBytes("utf-8"))
					+devi
					+shHelper.dfCompany.getMemberType();
		} catch (Exception e) {
			qr_text = "QR broken";
			e.printStackTrace();
		}
		createImage();
	}
	
	
	// 生成QR图
    private void createImage() {
    	final int QR_WIDTH = JackUtils.dip2px(this, 250),
				  QR_HEIGHT= JackUtils.dip2px(this, 250);
        try {
            // 需要引入core包
            QRCodeWriter writer = new QRCodeWriter();

            String text = qr_text;//.getText().toString();

            Log.i(TAG, "生成的文本：" + text);
            if (text == null || "".equals(text) || text.length() < 1) {
                return;
            }

            // 把输入的文本转为二维码
            BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
                    QR_WIDTH, QR_HEIGHT);

            System.out.println("w:" + martix.getWidth() + "h:"
                    + martix.getHeight());

            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }

                }
            }

            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);

            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            qr_image.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
	//model.code = [NSString stringWithFormat:@"%@?data=%@-|-%@-|-%@", shopSiteStr, [shopInfo objectForKey:@"shopId"], [GTMBase64 stringByEncodingData:[[shopInfo objectForKey:@"shopName"] dataUsingEncoding:NSUTF8StringEncoding]], [shopInfo objectForKey:@"shopLevel"]]; 

 /*
  * // 解析QR图片
    private void scanningImage() {

        Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");

        // 获得待解析的图片
        Bitmap bitmap = ((BitmapDrawable) qr_image.getDrawable()).getBitmap();
        RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result result;
        try {
            result = reader.decode(bitmap1, hints);
            // 得到解析后的文字
            qr_result.setText(result.getText());
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
    }
    */
	
}