#!/usr/bin/env bash

set -euo pipefail

bb_files="$(git diff --cached --name-only --diff-filter=ACMR | grep -E '\.bb$' || true)"

[ -z "${bb_files}" ] && exit 0

missing=()
while IFS= read -r file; do
    [ -z "${file}" ] && continue
    if ! git show ":${file}" | grep -Eq '^[[:space:]]*COMPATIBLE_MACHINE([[:alnum:]_:-]*)?[[:space:]]*([?+]?=)'; then
        missing+=("${file}")
    fi
done <<< "${bb_files}"

if [ ${#missing[@]} -gt 0 ]; then
    echo "ERROR: COMPATIBLE_MACHINE is missing in the following staged .bb files:"
    for file in "${missing[@]}"; do
        echo "  - ${file}"
    done
    echo "Add COMPATIBLE_MACHINE before committing."
    exit 1
fi

exit 0
