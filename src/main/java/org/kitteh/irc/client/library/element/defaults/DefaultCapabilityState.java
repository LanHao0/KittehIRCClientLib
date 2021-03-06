/*
 * * Copyright (C) 2013-2017 Matt Baxter http://kitteh.org
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.kitteh.irc.client.library.element.defaults;

import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.element.CapabilityState;
import org.kitteh.irc.client.library.util.ToStringer;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Default implementation of {@link CapabilityState}.
 */
public class DefaultCapabilityState implements CapabilityState {
    private final Client client;
    private final long creationTime;
    private final boolean disable;
    private final String name;
    private final Optional<String> value;

    /**
     * Constructs a capability state.
     *
     * @param client client
     * @param capabilityListItem capability list item including modifiers
     */
    public DefaultCapabilityState(@Nonnull Client client, @Nonnull String capabilityListItem) {
        this.client = client;
        this.creationTime = System.currentTimeMillis();
        this.disable = capabilityListItem.charAt(0) == '-';
        String remaining = this.disable ? capabilityListItem.substring(1) : capabilityListItem;
        int index = remaining.indexOf('=');
        if ((index > -1) && (remaining.length() > (index + 1))) {
            this.name = remaining.substring(0, index);
            this.value = Optional.of(remaining.substring(index + 1));
        } else {
            this.name = (index > -1) ? remaining.substring(0, index) : remaining;
            this.value = Optional.empty();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DefaultCapabilityState) {
            DefaultCapabilityState state = (DefaultCapabilityState) o;
            return state.name.equals(this.name) && (state.disable == this.disable);
        }
        return false;
    }

    @Override
    public boolean isDisabled() {
        return this.disable;
    }

    @Nonnull
    @Override
    public Client getClient() {
        return this.client;
    }

    @Override
    public long getCreationTime() {
        return this.creationTime;
    }

    @Nonnull
    @Override
    public String getName() {
        return this.name;
    }

    @Nonnull
    @Override
    public Optional<String> getValue() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return (2 * this.name.hashCode()) + (this.disable ? 1 : 0);
    }

    @Nonnull
    @Override
    public String toString() {
        return new ToStringer(this).add("name", this.name).add("disabled", this.disable).add("value", this.value).toString();
    }
}
