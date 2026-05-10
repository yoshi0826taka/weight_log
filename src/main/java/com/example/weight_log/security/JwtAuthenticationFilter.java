package com.example.weight_log.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.JwtException;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT をリクエストの Authorization ヘッダから抽出・検証し、
 * SecurityContext に認証情報を設定するフィルタ。
 *
 * 期待される利用：
 * - リクエストヘッダ: Authorization: Bearer <token>
 * - 検証成功時: UsernamePasswordAuthenticationToken を SecurityContext に設定
 * - 検証失敗時: リクエストを続行させ、@PreAuthorize 等で保護
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String jwt = extractTokenFromRequest(request);

            if (StringUtils.hasText(jwt)) {
                try {
                    // JWT を検証して subject（ユーザーID）を取得
                    String userId = jwtUtil.extractSubject(jwt);

                    // UsernamePasswordAuthenticationToken を作成して SecurityContext に設定
                    // principal: ユーザーID, credentials: null, authorities: 空（デフォルト権限なし）
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                } catch (JwtException e) {
                    // JWT 検証失敗時はログを出力するが、リクエストは続行
                    // （@PreAuthorize でエンドポイント保護させる想定）
                    logger.debug("JWT validation failed: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * リクエストから Bearer トークンを抽出します。
     * 期待フォーマット: Authorization: Bearer <token>
     *
     * @param request HttpServletRequest
     * @return 抽出されたトークン、存在しない場合は null
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
