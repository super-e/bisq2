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

package bisq.tor;

import bisq.tor.installer.TorInstaller;
import bisq.tor.process.LdPreload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class InstallerAndRunRaceConditionTest {
    @Test
    void test(@TempDir Path tempDir) throws IOException, InterruptedException {
        var torInstaller = new TorInstaller(tempDir);
        torInstaller.installIfNotUpToDate();

        Path torBinaryPath = tempDir.resolve("tor");
        var processBuilder = new ProcessBuilder(
                torBinaryPath.toAbsolutePath().toString(),
                "--version"
        );

        Map<String, String> environment = processBuilder.environment();
        environment.put("LD_PRELOAD", LdPreload.computeLdPreloadVariable(tempDir));

        Process process = processBuilder.start();
        boolean isSuccess = process.waitFor(30, TimeUnit.SECONDS);

        assertThat(isSuccess).isTrue();
        assertThat(process.exitValue()).isEqualTo(0);
    }
}
