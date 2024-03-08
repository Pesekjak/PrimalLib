package org.machinemc.primallib.profile;

import com.google.common.base.Preconditions;
import lombok.With;

import java.security.PublicKey;
import java.time.Instant;
import java.util.UUID;

/**
 * Represents a chat session of player.
 * <p>
 * This chat session is used for signed chat messages and should not
 * be changed.
 *
 * @param id id of the session, is different from player's UUID
 * @param expiresAt when the session expires
 * @param key public key of the session
 * @param signature signature of the session, maximal length is 4096
 */
@With
public record ChatSession(UUID id,
                          Instant expiresAt,
                          PublicKey key,
                          byte[] signature) {

    public ChatSession {
        Preconditions.checkNotNull(id, "ID can not be null");
        Preconditions.checkNotNull(expiresAt, "Expiration date can not be null");
        Preconditions.checkNotNull(key, "Public key can not be null");
        Preconditions.checkNotNull(signature, "Signature can not be null");
    }

    /**
     * Returns whether this chat session is already expired.
     *
     * @return if the session expired
     */
    public boolean isExpired() {
        return expiresAt.isBefore(Instant.now());
    }

}
