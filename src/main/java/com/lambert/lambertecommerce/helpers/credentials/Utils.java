package com.lambert.lambertecommerce.helpers.credentials;

import com.lambert.lambertecommerce.model.Credentials;
import com.lambert.lambertecommerce.model.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

public class Utils {

   public static boolean userIsLoggedIn(User user) {
      return false;
   }

   public static boolean userIsLoggedIn(Credentials credentials) {
      return false;
   }

   public static boolean userIsLoggedIn(Authentication authentication) {
      return !(authentication instanceof AnonymousAuthenticationToken);
   }
}
