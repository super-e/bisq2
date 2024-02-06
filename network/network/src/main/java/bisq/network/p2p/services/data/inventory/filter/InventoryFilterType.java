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

package bisq.network.p2p.services.data.inventory.filter;

import bisq.common.proto.ProtoEnum;
import bisq.common.util.ProtobufUtils;

public enum InventoryFilterType implements ProtoEnum {
    HASH_SET,
    MINI_SKETCH;

    @Override
    public bisq.network.protobuf.InventoryFilterType toProto() {
        return bisq.network.protobuf.InventoryFilterType.valueOf(getProtobufEnumPrefix() + name());
    }

    public static InventoryFilterType fromProto(bisq.network.protobuf.InventoryFilterType proto) {
        return ProtobufUtils.enumFromProto(InventoryFilterType.class, proto.name(), HASH_SET);
    }
}