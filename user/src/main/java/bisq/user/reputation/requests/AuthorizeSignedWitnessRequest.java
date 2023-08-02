/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package bisq.user.reputation.requests;

import bisq.common.proto.ProtoResolver;
import bisq.common.proto.UnresolvableProtobufMessageException;
import bisq.network.p2p.message.NetworkMessage;
import bisq.network.p2p.services.data.storage.MetaData;
import bisq.network.p2p.services.data.storage.mailbox.MailboxMessage;
import bisq.network.protobuf.ExternalNetworkMessage;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.TimeUnit;

@Getter
@ToString
@EqualsAndHashCode
public final class AuthorizeSignedWitnessRequest implements MailboxMessage {
    private final static long TTL = TimeUnit.DAYS.toMillis(2);

    private final MetaData metaData = new MetaData(TTL, 100_000, AuthorizeSignedWitnessRequest.class.getSimpleName());
    private final String profileId;
    private final String hashAsHex;
    private final long accountAgeWitnessDate;
    private final long witnessSignDate;
    private final String pubKeyBase64;
    private final String signatureBase64;

    public AuthorizeSignedWitnessRequest(String profileId,
                                         String hashAsHex,
                                         long accountAgeWitnessDate,
                                         long witnessSignDate,
                                         String pubKeyBase64,
                                         String signatureBase64) {
        this.profileId = profileId;
        this.hashAsHex = hashAsHex;
        this.accountAgeWitnessDate = accountAgeWitnessDate;
        this.witnessSignDate = witnessSignDate;
        this.pubKeyBase64 = pubKeyBase64;
        this.signatureBase64 = signatureBase64;
    }

    @Override
    public bisq.network.protobuf.NetworkMessage toProto() {
        return getNetworkMessageBuilder()
                .setExternalNetworkMessage(ExternalNetworkMessage.newBuilder()
                        .setAny(Any.pack(toAuthorizeSignedWitnessRequestProto())))
                .build();
    }

    public bisq.user.protobuf.AuthorizeSignedWitnessRequest toAuthorizeSignedWitnessRequestProto() {
        return bisq.user.protobuf.AuthorizeSignedWitnessRequest.newBuilder()
                .setProfileId(profileId)
                .setHashAsHex(hashAsHex)
                .setAccountAgeWitnessDate(accountAgeWitnessDate)
                .setWitnessSignDate(witnessSignDate)
                .setPubKeyBase64(pubKeyBase64)
                .setSignatureBase64(signatureBase64)
                .build();
    }

    public static AuthorizeSignedWitnessRequest fromProto(bisq.user.protobuf.AuthorizeSignedWitnessRequest proto) {
        return new AuthorizeSignedWitnessRequest(proto.getProfileId(),
                proto.getHashAsHex(),
                proto.getAccountAgeWitnessDate(),
                proto.getWitnessSignDate(),
                proto.getPubKeyBase64(),
                proto.getSignatureBase64());
    }

    public static ProtoResolver<NetworkMessage> getNetworkMessageResolver() {
        return any -> {
            try {
                bisq.user.protobuf.AuthorizeSignedWitnessRequest proto = any.unpack(bisq.user.protobuf.AuthorizeSignedWitnessRequest.class);
                return AuthorizeSignedWitnessRequest.fromProto(proto);
            } catch (InvalidProtocolBufferException e) {
                throw new UnresolvableProtobufMessageException(e);
            }
        };
    }
}