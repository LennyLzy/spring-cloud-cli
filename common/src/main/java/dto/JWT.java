package dto;

import lombok.Data;

/**
 * Created by Administrator on 2019/4/16.
 */
@Data
public class JWT {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private String jti;
}
