/*
 * Copyright (c) 2024 GeyserMC <https://geysermc.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/DatabaseUtils
 */
package org.geysermc.databaseutils.processor.type;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.lang.model.element.Modifier;
import org.geysermc.databaseutils.IRepository;
import org.geysermc.databaseutils.processor.info.EntityInfo;

public abstract class DatabaseGenerator {
    protected TypeSpec.Builder spec;
    protected boolean hasAsync;

    public void init(TypeSpec.Builder spec, boolean hasAsync) {
        if (this.spec != null) {
            throw new IllegalStateException("Cannot reinitialize RepositoryGenerator");
        }
        this.spec = spec;
        this.hasAsync = hasAsync;
    }

    public abstract Class<?> databaseClass();

    public abstract void addEntities(List<EntityInfo> entities);

    public void addRepositories(List<String> repositoriesClassName) {
        var builder = CodeBlock.builder().addStatement("REPOSITORIES = new $T<>()", ArrayList.class);
        for (String repository : repositoriesClassName) {
            builder.addStatement("REPOSITORIES.add($T::new)", ClassName.bestGuess(repository));
        }
        spec.addStaticBlock(builder.build());

        spec.addField(
                ParameterizedTypeName.get(
                        ClassName.get(List.class),
                        ParameterizedTypeName.get(Function.class, databaseClass(), IRepository.class)),
                "REPOSITORIES",
                Modifier.PRIVATE,
                Modifier.STATIC,
                Modifier.FINAL);
    }
}
