package dto;

public class CreateAndAuthUserResponse {
    private String accessToken;
    private String refreshToken;
    boolean success;
    User user;

    public CreateAndAuthUserResponse(String accessToken, String refreshToken, boolean success, User user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.success = success;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
