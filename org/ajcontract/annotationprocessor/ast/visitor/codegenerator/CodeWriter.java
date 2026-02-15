package org.ajcontract.annotationprocessor.ast.visitor.codegenerator;

import static javax.tools.StandardLocation.SOURCE_OUTPUT;

import java.io.IOException;
import java.io.Writer;
import javax.annotation.processing.Filer;
import javax.tools.FileObject;

/**
 * <i>Writer</i> wrapper that provides an
 * easy interface to perform code generation
 * tasks.
 */
public class CodeWriter {
    private static Filer filer;

    private Writer file;

    private int indentation;

    private CodeWriter(String packageName, String fileName) throws IOException {
        indentation = 0;
        FileObject fileObject = filer.createResource(SOURCE_OUTPUT, packageName, fileName);
        file = fileObject.openWriter();
    }

    public static void setFiler(Filer newFiler) {
        filer = newFiler;
    }

    public static CodeWriter createCodeWriter(String packageName, String fileName) throws IOException {
        return new CodeWriter(packageName, fileName);
    }

    public void close() {
        if (file != null) {
            try {
                file.close();
            } catch (IOException e) {
            }
        }
    }

    public void write(String code) throws IOException {
        for (int i = 0; i < indentation; i++) {
            file.append('\t');
        }
        file.append(code);
    }

    public void writeln() throws IOException {
        file.append('\n');
    }

    public void writeln(String codeLine) throws IOException {
        write(codeLine + '\n');
    }

    public void writeSentence(String codeSentence) throws IOException {
        write(codeSentence.trim() + ";\n");
    }

    public void beginBlock() throws IOException {
        file.append(" {\n");
        increaseIndent();
    }

    public void endBlock() throws IOException {
        decreaseIndent();
        writeln("}");
    }

    public void increaseIndent() {
        indentation++;
    }

    public void decreaseIndent() {
        if (indentation > 0) {
            indentation--;
        } else {
            throw new IllegalStateException();
        }
    }
}
